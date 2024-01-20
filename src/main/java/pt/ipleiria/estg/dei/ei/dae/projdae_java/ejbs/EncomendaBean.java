package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EncomendaProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EncomendaBean {
    @PersistenceContext
    private EntityManager em;

    private EncomendaProdutoBean encomendaProdutoBean;

    public Encomenda find(long id) {
        return em.find(Encomenda.class, id);
    }

    public List<Encomenda> getAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public List<Encomenda> getAllNaoAtribuidas() {
        return em.createNamedQuery("getAllEncomendasNaoAtribuidas", Encomenda.class).getResultList();
    }

    public List<Encomenda> getEncomendasOperador(String operadorUsername) {
        return em.createNamedQuery("getEncomendasOperador", Encomenda.class).setParameter("operadorUsername", operadorUsername).getResultList();
    }

    public Encomenda create(String clienteUsername, String morada, String estado, String armazem, long embalagemTranporteId, List<EncomendaProdutoDTO> encomendaProdutoDTOS) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        Cliente cliente = em.find(Cliente.class, clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }

        EmbalagemTransporte embalagemTransporte = em.find(EmbalagemTransporte.class, embalagemTranporteId);
        if (embalagemTransporte == null) {
            throw new MyEntityNotFoundException(
                    "embalagemTransporte '" + embalagemTranporteId + "' não existe");
        }

        Encomenda encomenda = null;
        try {
            encomenda = new Encomenda(cliente, morada, estado, armazem, embalagemTransporte);
            em.persist(encomenda);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }

        Produto produto = null;
        EncomendaProduto encomendaProduto = null;
        Sensor sensor = null;
        List<String> listaTiposSensores = null;
        for (EncomendaProdutoDTO encomendaProdutoDTO: encomendaProdutoDTOS) {
            encomendaProduto = encomendaProdutoBean.create(encomenda.getId(), encomendaProdutoDTO.getProdutoId(), encomendaProdutoDTO.getQuantidade());
            produto = em.find(Produto.class, encomendaProdutoDTO.getProdutoId());
            produto.addEncomendaProduto(encomendaProduto);
            encomenda.addEncomendaProduto(encomendaProduto);

            for(int i=0; i<encomendaProdutoDTO.getQuantidade(); i++){
                for (Regra regra: produto.getRegras()) {
                    if(!listaTiposSensores.contains(regra.getTipo_sensor())) {
                        listaTiposSensores.add(regra.getTipo_sensor());
                        sensor = new Sensor(regra.getTipo_sensor());
                        encomenda.addSensor(sensor);
                    }
                }
            }
            listaTiposSensores.clear();
        }

        cliente.addEncomenda(encomenda);
        embalagemTransporte.addEncomenda(encomenda);
        return encomenda;
    }

    public void update(long id, String operadorUsername, String clienteUsername, String morada, String estado, Date dataEntrega, String armazem, long embalagemTransporteId) throws MyEntityNotFoundException {
        Encomenda encomenda = em.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com id '" + id +"' não existe");
        }

        Operador operador = em.find(Operador.class, operadorUsername);
        if (operador == null) {
            throw new MyEntityNotFoundException(
                    "Operador '" + operadorUsername + "' não existe");
        }

        Cliente cliente = em.find(Cliente.class, clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }

        EmbalagemTransporte embalagemTransporte = em.find(EmbalagemTransporte.class, embalagemTransporteId);
        if (embalagemTransporte == null) {
            throw new MyEntityNotFoundException(
                    "EmbalagemTransporte '" + embalagemTransporteId + "' não existe");
        }

        em.lock(encomenda, LockModeType.OPTIMISTIC);

        if(encomenda.getOperador() == null){
            encomenda.setOperador(operador);
            operador.addEncomenda(encomenda);
        } else if (operador != encomenda.getOperador()) {
            encomenda.getOperador().removeEncomenda(encomenda);
            encomenda.setOperador(operador);
            operador.addEncomenda(encomenda);
        }

        if(cliente != encomenda.getCliente()){
            encomenda.getCliente().removeEncomenda(encomenda);
            encomenda.setCliente(cliente);
            cliente.addEncomenda(encomenda);
        }

        encomenda.setMorada(morada);
        encomenda.setEstado(estado);
        encomenda.setDataEntrega(dataEntrega);
        encomenda.setArmazem(armazem);

        if(embalagemTransporte != encomenda.getEmbalagemTransporte()){
            encomenda.getEmbalagemTransporte().removeEncomenda(encomenda);
            encomenda.setEmbalagemTransporte(embalagemTransporte);
            embalagemTransporte.addEncomenda(encomenda);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException{
        Encomenda encomenda = em.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com id '" + id +"' não existe");
        }
        List<EncomendaProduto> EncomendaProdutos = encomenda.getEncomendaProdutos();
        List<Sensor> sensores = encomenda.getSensores();

        if (EncomendaProdutos != null) {
            for (EncomendaProduto EncomendaProduto : EncomendaProdutos){
                em.lock(EncomendaProduto, LockModeType.OPTIMISTIC);
                EncomendaProduto.setEncomenda(null);
            }
            EncomendaProdutos.clear();
        }
        if (sensores != null) {
            for (Sensor sensor : sensores) {
                em.lock(sensor, LockModeType.OPTIMISTIC);
                sensor.setEncomenda(null);
            }
            sensores.clear();
        }

        Cliente cliente = encomenda.getCliente();
        cliente.removeEncomenda(encomenda);

        Operador operador = encomenda.getOperador();
        operador.removeEncomenda(encomenda);

        EmbalagemTransporte embalagemTransporte = encomenda.getEmbalagemTransporte();
        embalagemTransporte.removeEncomenda(encomenda);

        em.remove(encomenda);
    }
}

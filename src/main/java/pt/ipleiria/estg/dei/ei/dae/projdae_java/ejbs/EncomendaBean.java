package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

public class EncomendaBean {

    @PersistenceContext
    private EntityManager em;

    private ClienteBean clienteBean;

    private OperadorBean operadorBean;

    public Encomenda find(long codigo) {
        return em.find(Encomenda.class, codigo);
    }

    public List<Encomenda> getAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public void create(long codigo, String clienteUsername, String morada, String estado) throws MyEntityExistsException, MyEntityNotFoundException {
        Encomenda encomenda = find(codigo);

        if (encomenda != null) {
            throw new MyEntityExistsException(
                    "Encomenda com o codigo '" + codigo + "' ja existe");
        }
        Cliente cliente = clienteBean.find(clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }
        encomenda = new Encomenda(codigo, cliente, morada, estado);
        em.persist(encomenda);
    }

    public void update(long codigo, String clienteUsername,String operadorUsername, String morada, String estado) throws MyEntityNotFoundException {
        Encomenda encomenda = em.find(Encomenda.class, codigo);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com o código '" + codigo +"' não existe");
        }
        Cliente cliente = clienteBean.find(clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }
        Operador operador = operadorBean.find(operadorUsername);
        if (operador == null & operadorUsername != null) {
            throw new MyEntityNotFoundException(
                    "Operador '" + operadorUsername + "' não existe");
        }
        em.lock(encomenda, LockModeType.OPTIMISTIC);
        encomenda.setCliente(cliente);
        encomenda.setOperador(operador);
        encomenda.setMorada(morada);
        encomenda.setEstado(estado);
    }

    public void delete(long codigo) throws MyEntityNotFoundException{
        Encomenda encomenda = em.find(Encomenda.class, codigo);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com o código '" + codigo +"' não existe");
        }
        List<Produto> produtos = encomenda.getProdutos();
        List<EmbalagemTransporte> embalagemTransportes = encomenda.getEmbalagemTransportes();

        if (produtos != null) {
            produtos.clear();
        }
        if(embalagemTransportes != null){
            embalagemTransportes.clear();
        }

        em.remove(encomenda);
    }
}

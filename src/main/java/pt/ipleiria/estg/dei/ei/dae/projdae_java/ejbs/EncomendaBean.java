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

    public Encomenda find(long code) {
        return em.find(Encomenda.class, code);
    }

    public List<Encomenda> getAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public void create(long code, String clienteUsername, String address, String state) throws MyEntityExistsException, MyEntityNotFoundException {
        Encomenda encomenda = find(code);

        if (encomenda != null) {
            throw new MyEntityExistsException(
                    "Encomenda com o codigo '" + code + "' ja existe");
        }
        Cliente cliente = clienteBean.find(clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }
        encomenda = new Encomenda(code, cliente, address, state);
        em.persist(encomenda);
    }

    public void update(long code, String clienteUsername,String operadorUsername, String address, String state) throws MyEntityNotFoundException {
        Encomenda encomenda = em.find(Encomenda.class, code);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com o código '" + code +"' não existe");
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
        encomenda.setAddress(address);
        encomenda.setState(state);
    }

    public void delete(long code) throws MyEntityNotFoundException{
        Encomenda encomenda = em.find(Encomenda.class, code);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com o código '" + code +"' não existe");
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

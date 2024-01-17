package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

public class EncomendaBean {

    @PersistenceContext
    private EntityManager em;

    private ClienteBean clienteBean;

    private OperadorBean operadorBean;

    public Encomenda find(long id) {
        return em.find(Encomenda.class, id);
    }

    public List<Encomenda> getAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public void create(long id, String clienteUsername, String morada, String estado, String armazem, Date dataEntrega) throws MyEntityExistsException, MyEntityNotFoundException {
        Encomenda encomenda = find(id);

        if (encomenda != null) {
            throw new MyEntityExistsException(
                    "Encomenda com id '" + id + "' ja existe");
        }
        Cliente cliente = clienteBean.find(clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }
        encomenda = new Encomenda(id, cliente, morada, estado, armazem, dataEntrega);
        cliente.addEncomenda(encomenda);
        em.persist(encomenda);
    }

    public void update(long id, String clienteUsername, String morada, String estado, String armazem, Date dataEntrega, String operadorUsername) throws MyEntityNotFoundException {
        Encomenda encomenda = em.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com id '" + id +"' não existe");
        }

        Cliente cliente = clienteBean.find(clienteUsername);
        if (cliente == null) {
            throw new MyEntityNotFoundException(
                    "Cliente '" + clienteUsername + "' não existe");
        }
        if(cliente != encomenda.getCliente()){
            encomenda.getCliente().removeEncomenda(encomenda);
            encomenda.setCliente(cliente);
            cliente.addEncomenda(encomenda);
        }
        Operador operador = operadorBean.find(operadorUsername);
        if (operador == null & operadorUsername != null) {
            throw new MyEntityNotFoundException(
                    "Operador '" + operadorUsername + "' não existe");
        }
        if(operador != encomenda.getOperador()){
            encomenda.getOperador().removeEncomenda(encomenda);
            encomenda.setOperador(operador);
            operador.addEncomenda(encomenda);
        }
        em.lock(encomenda, LockModeType.OPTIMISTIC);
        encomenda.setMorada(morada);
        encomenda.setEstado(estado);
    }

    public void delete(long id) throws MyEntityNotFoundException{
        Encomenda encomenda = em.find(Encomenda.class, id);
        if (encomenda == null) {
            throw new MyEntityNotFoundException("Encomenda com id '" + id +"' não existe");
        }
        List<Produto> produtos = encomenda.getProdutos();
        List<EmbalagemTransporte> embalagemTransportes = encomenda.getEmbalagemTransportes();

        if (produtos != null) {
            produtos.clear();
            for (Produto produto : produtos){
                em.lock(produto, LockModeType.OPTIMISTIC);
                produto.setEncomenda(null);
            }
        }
        if(embalagemTransportes != null){
            embalagemTransportes.clear();
        }

        Cliente cliente = encomenda.getCliente();
        Operador operador = encomenda.getOperador();
        cliente.removeEncomenda(encomenda);
        operador.removeEncomenda(encomenda);
        em.remove(encomenda);
    }

    public void enrollProdutoInEncomenda(long produtoId, long encomendaId){
        Produto produto = em.find(Produto.class, produtoId);
        Encomenda encomenda = find(encomendaId);

        if(produto == null || encomenda == null || produto.getEncomenda() != null || encomenda.getProdutos().contains(produto)){
            return;
        }

        em.lock(encomenda, LockModeType.OPTIMISTIC);
        produto.setEncomenda(encomenda);
        encomenda.addProduto(produto);
    }

    public void unrollProdutoInEncomenda(long produtoId, long encomendaId){
        Produto produto = em.find(Produto.class, produtoId);
        Encomenda encomenda = find(encomendaId);

        if(produto == null || encomenda == null || produto.getEncomenda() == null || !(encomenda.getProdutos().contains(produto))){
            return;
        }

        em.lock(encomenda, LockModeType.OPTIMISTIC);
        produto.setEncomenda(null);
        encomenda.removeProduto(produto);
    }
}

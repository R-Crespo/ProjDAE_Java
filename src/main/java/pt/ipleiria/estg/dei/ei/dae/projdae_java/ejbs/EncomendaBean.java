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

    public Encomenda find(long code) {
        return em.find(Encomenda.class, code);
    }

    public List<Encomenda> getAll() {
        return em.createNamedQuery("getAllEncomendas", Encomenda.class).getResultList();
    }

    public void create(long code, String clienteUsername, String address, String state, String warehouse, Date deliveryDate) throws MyEntityExistsException, MyEntityNotFoundException {
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
        encomenda = new Encomenda(code, cliente, address, state, warehouse, deliveryDate);
        cliente.addEncomenda(encomenda);
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

    public void enrollProdutoInEncomenda(long produtoCode, long encomendaCode){
        Produto produto = em.find(Produto.class, produtoCode);
        Encomenda encomenda = find(encomendaCode);

        if(produto == null || encomenda == null || produto.getEncomenda() != null || encomenda.getProdutos().contains(produto)){
            return;
        }

        em.lock(encomenda, LockModeType.OPTIMISTIC);
        produto.setEncomenda(encomenda);
        encomenda.addProduto(produto);
    }

    public void unrollProdutoInEncomenda(long produtoCode, long encomendaCode){
        Produto produto = em.find(Produto.class, produtoCode);
        Encomenda encomenda = find(encomendaCode);

        if(produto == null || encomenda == null || produto.getEncomenda() == null || !(encomenda.getProdutos().contains(produto))){
            return;
        }

        em.lock(encomenda, LockModeType.OPTIMISTIC);
        produto.setEncomenda(null);
        encomenda.removeProduto(produto);
    }
}

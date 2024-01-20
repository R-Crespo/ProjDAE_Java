package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoBean {

    @PersistenceContext
    private EntityManager em;

    private EncomendaBean encomendaBean;

    private AdministratorBean administratorBean;

    public Produto find(long id){
        return em.find(Produto.class, id);
    }

    public List<Produto> getAll() {
        return em.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public void create(long id, String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao) throws MyEntityNotFoundException,MyEntityExistsException{
        Produto produto = find(id);
        if(produto != null){
            throw new MyEntityExistsException("Produto com id '"+ id +"' já existe");
        }

        produto = new Produto(id, nome, tipo, marca, quantidade, unidadeMedida, preco, descricao);
        em.persist(produto);
    }

    public void update(long id, String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao) throws MyEntityNotFoundException {
        Produto produto = em.find(Produto.class, id);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto com id '" + id + "' não existe");
        }
        em.lock(produto, LockModeType.OPTIMISTIC);
        produto.setNome(nome);
        produto.setTipo(tipo);
        produto.setPreco(preco);
        produto.setDescricao(descricao);
        produto.setMarca(marca);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setQuantidade(quantidade);

    }

    public void delete(long id) throws  MyEntityNotFoundException,MyConstraintViolationException {
        try {
            Produto produto = em.find(Produto.class, id);
            if (produto == null) {
                throw new MyEntityNotFoundException("Produto com id '" + id + "' não existe");
            }
            em.remove(produto.getEmbalagemProduto());
            em.remove(produto);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }


}

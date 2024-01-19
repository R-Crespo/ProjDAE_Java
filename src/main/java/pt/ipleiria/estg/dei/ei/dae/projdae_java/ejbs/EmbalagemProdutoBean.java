package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EmbalagemProdutoBean {
    @PersistenceContext
    private EntityManager em;
    private ProdutoBean produtoBean;

    public EmbalagemProduto find(long id) {
        return em.find(EmbalagemProduto.class, id);
    }

    public List<EmbalagemProduto> getAll(){
        return em.createNamedQuery("getAllEmbalagensProduto", EmbalagemProduto.class).getResultList();
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(e.id) FROM EmbalagemProduto e WHERE e.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(long id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, long produtoId) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException("Embalagem produto com id '" + id + "' já existe");
        }

        Produto produto = produtoBean.find(produtoId);
        if(produto == null){
            throw new MyEntityNotFoundException("Produto com id "+ produtoId + " não existe");
        }

        EmbalagemProduto embalagemProduto = null;
        try {
            embalagemProduto = new EmbalagemProduto(id, tipo, funcao, dataFabrico, material, peso, volume, produto);
            produto.addEmbalagemProduto(embalagemProduto);
            em.persist(embalagemProduto);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public EmbalagemProduto delete(long id) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = find(id);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("EmbalagemProduto com id '" + id + "' não existe");
        }

        em.remove(embalagemProduto);
        return embalagemProduto;
    }
}

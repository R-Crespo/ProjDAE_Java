package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EmbalagemProdutoBean {
    @PersistenceContext
    private EntityManager em;

    public EmbalagemProduto find(int id) {
        return em.find(EmbalagemProduto.class, id);
    }

    public List<EmbalagemProduto> getAll(){
        return em.createNamedQuery("getAllEmbalagensProduto", EmbalagemProduto.class).getResultList();
    }

    public boolean exists(int id) {
        Query query = em.createQuery(
                "SELECT COUNT(e.id) FROM EmbalagemProduto e WHERE e.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(int id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) throws MyEntityExistsException,/* MyEntityNotFoundException,*/ MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException("EmbalagemProduto with id '" + id + "' already exists");
        }
        EmbalagemProduto embalagemProduto = null;

        try {
            embalagemProduto = new EmbalagemProduto(id, tipo, funcao, dataFabrico, material, peso, volume);
            em.persist(embalagemProduto);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public EmbalagemProduto delete(int id) throws MyEntityNotFoundException {
        EmbalagemProduto embalagemProduto = find(id);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("EmbalagemProduto with id '" + id + "' not found");
        }
        /* TODO
        for (Produto embalagemProdutoProduto : embalagemProduto.getProdutos()) {
            embalagemProtutoProduto.removeEmbalagem(embalagemProduto);
        }*/
        em.remove(embalagemProduto);
        return embalagemProduto;
    }
}

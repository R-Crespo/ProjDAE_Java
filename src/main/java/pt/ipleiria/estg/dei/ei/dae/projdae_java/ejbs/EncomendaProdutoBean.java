package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EncomendaProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@Stateless
public class EncomendaProdutoBean {
    @PersistenceContext
    private EntityManager em;

    public EncomendaProduto find(long id) {
        return em.find(EncomendaProduto.class, id);
    }
    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(p.id) FROM EncomendaProduto p WHERE p.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public EncomendaProduto create(long encomendaId, long produtoId, int quantidade) throws MyEntityNotFoundException, MyConstraintViolationException {
        Encomenda encomenda = em.find(Encomenda.class, encomendaId);
        if (encomenda == null) {
            throw new MyEntityNotFoundException(
                    "Encomenda com id '" + encomendaId + "' não existe"
            );
        }

        Produto produto = em.find(Produto.class, produtoId);
        if (produto == null) {
            throw new MyEntityNotFoundException(
                    "Produto com id '" + produtoId + "' não existe"
            );
        }

        EncomendaProduto encomendaProduto = null;
        try {
            encomendaProduto = new EncomendaProduto(encomenda, produto, quantidade);
            em.persist(encomendaProduto);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        encomenda.addEncomendaProduto(encomendaProduto);
        produto.addEncomendaProduto(encomendaProduto);

        return encomendaProduto;
    }

    public EncomendaProduto delete(long id) throws MyEntityNotFoundException {
        EncomendaProduto encomendaProduto = find(id);
        if (encomendaProduto == null) {
            throw new MyEntityNotFoundException("EncomendaProduto com id '" + id + "' não existe");
        }
        Encomenda encomenda = encomendaProduto.getEncomenda();
        encomenda.removeEncomendaProduto(encomendaProduto);

        Produto produto = encomendaProduto.getProduto();
        produto.removeEncomendaProduto(encomendaProduto);

        em.remove(encomendaProduto);
        return encomendaProduto;
    }
}

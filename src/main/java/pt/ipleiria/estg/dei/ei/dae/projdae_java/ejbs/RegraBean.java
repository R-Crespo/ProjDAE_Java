package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Regra;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;
@Stateless
public class RegraBean {
    @PersistenceContext
    private EntityManager em;

    public Regra find(long id) {
        return em.find(Regra.class, id);
    }

    public List<Regra> getAll(){
        return em.createNamedQuery("getAllRegras", Regra.class).getResultList();
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(r.id) FROM Regra r WHERE r.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(int valor, String comparador, String mensagem, String tipo_sensor, Produto produto) throws MyEntityExistsException,MyEntityNotFoundException, MyConstraintViolationException {
        Regra regra = null;
        try {
            regra = new Regra(valor, comparador, mensagem, tipo_sensor, produto);
            produto.addRegra(regra);
            em.persist(regra);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    /*public Regra delete(int id) throws MyEntityNotFoundException {
        Regra regra = find(id);
        if (regra == null) {
            throw new MyEntityNotFoundException("Regra with id '" + id + "' not found");
        }

        for (Produto produtoRegra : regra.getProdutos()) {
            produtoRegra.removeRegra(regra);
        }
        em.remove(regra);
        return regra;
    }*/
}

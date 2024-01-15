package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EmbalagemTransporteBean {
    @PersistenceContext
    private EntityManager em;

    public EmbalagemTransporte find(int id) {
        return em.find(EmbalagemTransporte.class, id);
    }

    public List<EmbalagemTransporte> getAll(){
        return em.createNamedQuery("getAllEmbalagensTransporte", EmbalagemTransporte.class).getResultList();
    }

    public boolean exists(int id) {
        Query query = em.createQuery(
                "SELECT COUNT(e.id) FROM EmbalagemTransporte e WHERE e.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(int id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) throws MyEntityExistsException, MyConstraintViolationException {
        if (exists(id)) {
            throw new MyEntityExistsException("EmbalagemTransporte with id '" + id + "' already exists");
        }
        EmbalagemTransporte embalagemTransporte = null;

        try {
            embalagemTransporte = new EmbalagemTransporte(id, tipo, funcao, dataFabrico, material, peso, volume);
            em.persist(embalagemTransporte);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }


    public EmbalagemTransporte delete(int id) throws MyEntityNotFoundException {
        EmbalagemTransporte embalagemTransporte = find(id);
        if (embalagemTransporte == null) {
            throw new MyEntityNotFoundException("EmbalagemTransporte with id '" + id + "' not found");
        }
        /* TODO
        for (Encomenda embalagemTransporteEncomenda : embalagemTransporte.getEncomendas()) {
            embalagemTransporteEncomenda.removeEmbalagem(embalagemTransporte);
        }*/
        em.remove(embalagemTransporte);
        return embalagemTransporte;
    }
}


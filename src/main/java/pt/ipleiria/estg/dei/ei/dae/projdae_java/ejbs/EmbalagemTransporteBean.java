package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EmbalagemTransporteBean {
    @PersistenceContext
    private EntityManager em;

    private EncomendaBean encomendaBean;

    public EmbalagemTransporte find(long id) {
        return em.find(EmbalagemTransporte.class, id);
    }

    public List<EmbalagemTransporte> getAll(){
        return em.createNamedQuery("getAllEmbalagensTransporte", EmbalagemTransporte.class).getResultList();
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(e.id) FROM EmbalagemTransporte e WHERE e.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(long id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, long encomendaId) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        if (exists(id)) {
            throw new MyEntityExistsException("EmbalagemTransporte com id '" + id + "' já existe");
        }
        Encomenda encomenda = encomendaBean.find(encomendaId);
        if(encomenda == null){
            throw new MyEntityNotFoundException("Encomenda com id "+ encomendaId + " não existe");
        }

        EmbalagemTransporte embalagemTransporte = null;

        try {
            embalagemTransporte = new EmbalagemTransporte(id, tipo, funcao, dataFabrico, material, peso, volume);
            em.persist(embalagemTransporte);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }


    public EmbalagemTransporte delete(long id) throws MyEntityNotFoundException {
        EmbalagemTransporte embalagemTransporte = find(id);
        if (embalagemTransporte == null) {
            throw new MyEntityNotFoundException("EmbalagemTransporte com id '" + id + "' não existe");
        }
        List<Encomenda> encomendas = embalagemTransporte.getEncomendas();
        if (encomendas != null) {
            for (Encomenda encomenda : encomendas) {
                em.lock(encomenda, LockModeType.OPTIMISTIC);
                encomenda.setEmbalagemTransporte(null); // Supondo que existe um método setEncomenda em Sensor
            }
            encomendas.clear();
        }
        em.remove(embalagemTransporte);
        return embalagemTransporte;
    }
}


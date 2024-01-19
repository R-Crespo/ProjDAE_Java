package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;
@Stateless

public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public Sensor find(long id) {
        return em.find(Sensor.class, id);
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(s.id) FROM Sensor s WHERE s.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public void create(long id,String nome, long embalagemId) throws MyEntityExistsException, MyEntityNotFoundException {
        Sensor sensor = find(id);

        if(exists(id)){
            throw new MyEntityExistsException("Sensor " + nome + " já existe");
        }

        Embalagem embalagem = em.find(Embalagem.class, embalagemId);
        if (embalagem == null) {
            throw new MyEntityNotFoundException(
                    "Embalagem com id '" + embalagemId + "' não existe"
            );
        }

        sensor = new Sensor(id,nome, embalagem);
        em.persist(sensor);
    }

    public void update(long id, String nome) throws MyEntityNotFoundException {
        Sensor sensor = find(id);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor "+ id +" não existe");
        }
        em.lock(sensor, LockModeType.OPTIMISTIC);
        sensor.setNome(nome);
    }

    public void delete(long id) throws MyEntityNotFoundException{
        Sensor sensor = find(id);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor "+ id +" não existe");
        }

        List<Observacao> observacoes = sensor.getObservacoes();

        if (observacoes != null) {
            observacoes.clear();
            for(Observacao observacao : observacoes){
                em.remove(observacao);
            }
        }

        Embalagem embalagem = sensor.getEmbalagem();
        embalagem.removeSensor(sensor);
        em.remove(sensor);
    }
}

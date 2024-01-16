package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public Sensor find(long id) {
        return em.find(Sensor.class, id);
    }

    public void create(long id,String name) throws MyEntityExistsException {
        Sensor sensor = find(id);

        if(sensor != null){
            throw new MyEntityExistsException("Sensor " + name + " já existe");
        }

        sensor = new Sensor(id,name);
        em.persist(sensor);
    }

    public void update(long id, String name) throws MyEntityNotFoundException {
        Sensor sensor = find(id);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor "+ id +" não existe");
        }
        em.lock(sensor, LockModeType.OPTIMISTIC);
        sensor.setName(name);
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
        em.remove(sensor);
    }
}

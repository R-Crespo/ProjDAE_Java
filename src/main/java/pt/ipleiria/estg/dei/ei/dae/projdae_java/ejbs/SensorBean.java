package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

public class SensorBean {
    @PersistenceContext
    private EntityManager em;

    public Sensor find(String name) {
        return em.find(Sensor.class, name);
    }

    public void create(String name) throws MyEntityExistsException {
        Sensor sensor = find(name);

        if(sensor != null){
            throw new MyEntityExistsException("Sensor " + name + " já existe");
        }

        sensor = new Sensor(name);
        em.persist(sensor);
    }

    public void update(String name) throws MyEntityNotFoundException {
        Sensor sensor = find(name);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor "+ name +" não existe");
        }
        em.lock(sensor, LockModeType.OPTIMISTIC);
        sensor.setName(name);
    }

    public void delete(String name) throws MyEntityNotFoundException{
        Sensor sensor = find(name);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor "+ name +" não existe");
        }

        List<Observacao> observacoes = sensor.getObservacoes();
        List<EmbalagemTransporte> embalagemTransportes = sensor.getEmbalagemTransportes();

        if (observacoes != null) {
            observacoes.clear();
        }
        if(embalagemTransportes != null){
            embalagemTransportes.clear();
        }

        em.remove(sensor);
    }
}

package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;

public class ObservacaoBean {

    @PersistenceContext
    private EntityManager em;

    private SensorBean sensorBean;

    public Observacao find(long id) {
        return em.find(Observacao.class, id);
    }

    public void create(long id, long sensorId, String tipo, double valor, String unidadeMedida) throws MyEntityExistsException, MyEntityNotFoundException {
        Observacao observacao = find(id);
        Sensor sensor = sensorBean.find(sensorId);

        if(observacao != null){
            throw new MyEntityExistsException("Observacão com o codigo '" + id +"' já existe");
        }

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor com o ID '" + sensorId +"' não existe");
        }

        Date timestamp = new Date();
        observacao = new Observacao(id, sensor, timestamp ,tipo, valor, unidadeMedida);
        sensor.addObservacao(observacao);
        em.persist(observacao);
    }

    public void delete(long id) throws MyEntityNotFoundException{
        Observacao observacao = find(id);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacão com id '" + id +"' não existe");
        }

        Sensor sensor = observacao.getSensor();
        sensor.removeObservacao(observacao);
        em.remove(observacao);
    }
}

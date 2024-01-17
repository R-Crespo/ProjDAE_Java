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

    public Observacao find(long code) {
        return em.find(Observacao.class, code);
    }

    public void create(long code, long sensorId, long value, String type, String unit, Date timestamp) throws MyEntityExistsException, MyEntityNotFoundException {
        Observacao observacao = find(code);
        Sensor sensor = sensorBean.find(sensorId);

        if(observacao != null){
            throw new MyEntityExistsException("Observacão com o codigo '" + code +"' já existe");
        }

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor com o ID '" + sensorId +"' não existe");
        }

        observacao = new Observacao(code, type, sensor,value, unit, timestamp);
        sensor.addObservacao(observacao);
        em.persist(observacao);
    }

    public void update(long code, long sensorId, long value, String type, String unit, Date timestamp) throws MyEntityNotFoundException {
        Observacao observacao = find(code);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacão com o codigo '" + code +"' não existe");
        }

        Sensor sensor = sensorBean.find(sensorId);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor com o ID '" + sensorId +"' não existe");
        }
        em.lock(observacao, LockModeType.OPTIMISTIC);
        if(sensor != observacao.getSensor()){
            observacao.getSensor().removeObservacao(observacao);
            observacao.setSensor(sensor);
            sensor.addObservacao(observacao);
        }
        
        observacao.setType(type);
        observacao.setUnit(unit);
        observacao.setValue(value);
        observacao.setTimestamp(timestamp);
    }

    public void delete(long code) throws MyEntityNotFoundException{
        Observacao observacao = find(code);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacão com o codigo '" + code +"' não existe");
        }

        Sensor sensor = observacao.getSensor();
        sensor.removeObservacao(observacao);
        em.remove(observacao);
    }
}

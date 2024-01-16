package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Observacao;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

public class ObservacaoBean {

    @PersistenceContext
    private EntityManager em;

    private SensorBean sensorBean;

    public Observacao find(long code) {
        return em.find(Observacao.class, code);
    }

    public void create(long code, String description) throws MyEntityExistsException {
        Observacao observacao = find(code);

        if(observacao != null){
            throw new MyEntityExistsException("Observacão com o codigo '" + code +"' já existe");
        }

        observacao = new Observacao(code, description);
        em.persist(observacao);
    }

    public void update(long code, String description, String name) throws MyEntityNotFoundException {
        Observacao observacao = find(code);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacão com o codigo '" + code +"' não existe");
        }

        Sensor sensor = sensorBean.find(name);

        if(sensor == null){
            throw new MyEntityNotFoundException("Sensor com o nome '" + name +"' não existe");
        }

        em.lock(observacao, LockModeType.OPTIMISTIC);
        observacao.setDescription(description);
        observacao.setSensor(sensor);
    }

    public void delete(long code) throws MyEntityNotFoundException{
        Observacao observacao = find(code);

        if(observacao == null){
            throw new MyEntityNotFoundException("Observacão com o codigo '" + code +"' não existe");
        }

        em.remove(observacao);
    }
}

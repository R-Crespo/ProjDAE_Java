package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
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

    public void create(long id,String nome) throws MyEntityExistsException, MyEntityNotFoundException {
        Sensor sensor = find(id);

        if(exists(id)){
            throw new MyEntityExistsException("Sensor " + nome + " já existe");
        }

        sensor = new Sensor(id,nome);
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

    public List<Sensor> getSensoresAtivos(){
        List<Long> encomendasIds = em.createQuery("SELECT e.id FROM Encomenda e WHERE e.estado = :estado", Long.class)
            .setParameter("estado", "EmProgresso")
            .getResultList();

        if (!encomendasIds.isEmpty()) {
            return em.createQuery(
                            "SELECT s FROM Sensor s WHERE s.encomenda.id IN :encomendasIds", Sensor.class)
                    .setParameter("encomendasIds", encomendasIds)
                    .getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    public Observacao getUltimaObservacao(Sensor sensor) {
        if (sensor == null || sensor.getObservacoes().isEmpty()) {
            return new Observacao();
        }

        List<Observacao> observacoes = sensor.getObservacoes();
        return observacoes.get(observacoes.size() - 1);
    }
}

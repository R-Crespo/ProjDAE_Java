package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.SensorDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EmbalagemTransporte;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
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

    public EmbalagemTransporte getEmbalagemTransporte(){
        List<EmbalagemTransporte> embalagens = em.createNamedQuery("getAllEmbalagensTransporte", EmbalagemTransporte.class).getResultList();
        if (!embalagens.isEmpty()) {
            return embalagens.get(0);
        } else {
            // Tratar o caso em que não há resultados
            return new EmbalagemTransporte(); // ou lançar uma exceção personalizada
        }
    }

    public boolean exists() {
        if(em.createNamedQuery("getAllEmbalagensTransporte", EmbalagemTransporte.class).getResultList().isEmpty()){
            return false;
        }
        return true;
    }

    public void create(String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        EmbalagemTransporte embalagemTransporte = null;
        try {
            embalagemTransporte = new EmbalagemTransporte(tipo, funcao, dataFabrico, material, peso, volume);
            em.persist(embalagemTransporte);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
    }

    public void update(long id, String tipo, String funcao,String material, int peso, int volume, List<SensorDTO> sensores) throws MyEntityNotFoundException {
        if(!exists()){
            throw new MyEntityNotFoundException("EmbalagemTransporte nao existe ainda");
        }
        EmbalagemTransporte embalagemTransporte = getEmbalagemTransporte();
        embalagemTransporte.setTipo(tipo);
        embalagemTransporte.setFuncao(funcao);
        embalagemTransporte.setMaterial(material);
        embalagemTransporte.setPeso(peso);
        embalagemTransporte.setVolume(volume);

        // Atualiza os sensores
        List<Sensor> sensoresAtuais = embalagemTransporte.getSensores();

        for (SensorDTO sensorDTO : sensores) {
            Sensor sensor = sensoresAtuais.isEmpty() ? null : sensoresAtuais.stream()
                    .filter(s -> sensorDTO.getId() == s.getId())
                    .findFirst()
                    .orElse(null);

            if (sensor != null) {
                sensoresAtuais.remove(sensor);
            } else {
                // Adiciona novo sensor
                sensor = new Sensor(sensorDTO.getNome());
                sensor.setEmbalagem(embalagemTransporte);
                embalagemTransporte.addSensor(sensor);
                em.persist(sensor);
            }
        }

        for (Sensor sensorRemovido : sensoresAtuais) {
            embalagemTransporte.removeSensor(sensorRemovido);
            em.remove(sensorRemovido);
        }

        em.merge(embalagemTransporte);
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


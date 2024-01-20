package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;


import jakarta.ejb.EJB;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class ObservacaoBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private EncomendaBean encomendaBean;

    @EJB
    private ProdutoBean produtoBean;

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


    public List<Observacao> getObservacoesPorEncomenda(long encomendaId) {
        List<Observacao> observacoes = new ArrayList<>();
        Encomenda encomenda = em.find(Encomenda.class, encomendaId);

        if (encomenda == null) {
            return observacoes; // Retorna lista vazia se a encomenda não existir
        }

        // Adiciona observações dos sensores associados diretamente à encomenda
        for (Sensor sensor : encomenda.getSensores()) {
            observacoes.addAll(sensor.getObservacoes());
        }



        return observacoes;
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

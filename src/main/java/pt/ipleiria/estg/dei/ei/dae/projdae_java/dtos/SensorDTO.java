package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Embalagem;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Observacao;

import java.io.Serializable;

public class SensorDTO implements Serializable {
    @Id
    private long id;

    private String nome;

    private Long encomendaId;

    private Long embalagemId;

    private ObservacaoDTO ultimaObservação;

    public SensorDTO(long id, String nome, Long encomendaId, ObservacaoDTO ultimaObservação){
        this.id = id;
        this.nome = nome;
        this.encomendaId = encomendaId;
        this.ultimaObservação = ultimaObservação;
    }

    public SensorDTO(long id, String nome, Long embalagem){
        this.id = id;
        this.embalagemId = embalagem;
        this.nome = nome;
    }

    public SensorDTO(long id, String nome){
        this.id = id;
        this.nome = nome;
    }

    public SensorDTO(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(Long encomendaId) {
        this.encomendaId = encomendaId;
    }

    public ObservacaoDTO getUltimaObservação() {
        return ultimaObservação;
    }

    public void setUltimaObservação(ObservacaoDTO ultimaObservação) {
        this.ultimaObservação = ultimaObservação;
    }

    public Long getEmbalagemId() {
        return embalagemId;
    }

    public void setEmbalagemId(Long embalagemId) {
        this.embalagemId = embalagemId;
    }
}

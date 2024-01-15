package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sensor implements Serializable {
    @Id
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "sensor_embalagem",
            joinColumns = @JoinColumn(
                    name = "sensor_name",
                    referencedColumnName = "name"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_code",
                    referencedColumnName = "code"
            )
    )
    @NotNull
    private List<EmbalagemTransporte> embalagemTransportes = new ArrayList<>();
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Observacao> observacoes;

    public Sensor() {
    }

    public Sensor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmbalagemTransporte> getEmbalagemTransportes() {
        return embalagemTransportes;
    }

    public void setEmbalagemTransportes(List<EmbalagemTransporte> embalagemTransportes) {
        this.embalagemTransportes = embalagemTransportes;
    }

    public List<Observacao> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<Observacao> observacoes) {
        this.observacoes = observacoes;
    }
}

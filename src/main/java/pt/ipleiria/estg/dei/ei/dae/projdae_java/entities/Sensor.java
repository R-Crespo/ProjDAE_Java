package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "sensores")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllSensores",
                query = "SELECT s FROM Sensor s ORDER BY s.id" // JPQL
        )
})
public class Sensor implements Serializable {
    @Id
    private long id;
    @NotNull
    private String name;
    @ManyToOne
    @JoinColumn(name = "embalagem_id")
    private Embalagem embalagem;
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Observacao> observacoes;

    public Sensor() {
        this.id = 0;
        this.name = "";
        this.embalagem = new Embalagem();
    }

    public Sensor(long id,String name) {
        this.id = id;
        this.name = name;
    }

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Observacao> getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(List<Observacao> observacoes) {
        this.observacoes = observacoes;
    }

    public void addObservacao(Observacao observacao){
        if(observacao == null || observacoes.contains(observacao)){
            return;
        }

        observacoes.add(observacao);
    }

    public void removeObservacao(Observacao observacao){
        if(observacao == null || !(observacoes.contains(observacao))){
            return;
        }

        observacoes.remove(observacao);
    }
}

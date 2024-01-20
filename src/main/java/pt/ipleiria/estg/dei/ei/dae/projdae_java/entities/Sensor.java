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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;

    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;

    @ManyToOne
    @JoinColumn(name = "embalagem_id")
    private Embalagem embalagem;
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("timestamp DESC")
    private List<Observacao> observacoes;

    public Sensor() {
        this.observacoes = new ArrayList<Observacao>();
    }

    public Sensor(String nome) {
        this.nome = nome;
        this.observacoes = new ArrayList<Observacao>();
    }

    public Sensor(String nome, Embalagem embalagem) {
        this.nome = nome;
        this.embalagem = embalagem;
        this.observacoes = new ArrayList<Observacao>();
    }

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

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

    public List<Observacao> getObservacoes() {
        return new ArrayList<>(observacoes);
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

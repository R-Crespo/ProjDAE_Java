package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "regras")
@NamedQueries({
        @NamedQuery(
                name = "getAllRegras",
                query = "SELECT r FROM Regra r ORDER BY r.id" // JPQL
        )
})
public class Regra {
    @Version
    private long version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int valor;
    private String comparador;
    private String mensagem;
    private String tipo_sensor;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    @NotNull
    private Produto produto;

    public Regra() {
    }

    public Regra(int valor, String comparador, String mensagem, String tipo_sensor, Produto produto) {
        this.valor = valor;
        this.comparador = comparador;
        this.mensagem = mensagem;
        this.tipo_sensor = tipo_sensor;
        this.produto = produto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getComparador() {
        return comparador;
    }

    public void setComparador(String comparador) {
        this.comparador = comparador;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTipo_sensor() {
        return tipo_sensor;
    }

    public void setTipo_sensor(String tipo_sensor) {
        this.tipo_sensor = tipo_sensor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

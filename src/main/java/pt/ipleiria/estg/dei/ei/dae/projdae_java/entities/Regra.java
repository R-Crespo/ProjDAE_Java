package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "regras")
@NamedQueries({
        @NamedQuery(
                name = "getAllRegras",
                query = "SELECT r FROM Regra r ORDER BY r.id" // JPQL
        ),
        @NamedQuery(
                name = "getAllRegrasProduto",
                query = "SELECT r FROM Regra r WHERE r.produto.id = :produtoId ORDER BY r.id" // JPQL
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
    private String tipoSensor;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    @NotNull
    private Produto produto;

    public Regra() {
    }

    public Regra(int valor, String comparador, String mensagem, String tipoSensor, Produto produto) {
        this.valor = valor;
        this.comparador = comparador;
        this.mensagem = mensagem;
        this.tipoSensor = tipoSensor;
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

    public String getTipoSensor() {
        return tipoSensor;
    }

    public void setTipoSensor(String tipoSensor) {
        this.tipoSensor = tipoSensor;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

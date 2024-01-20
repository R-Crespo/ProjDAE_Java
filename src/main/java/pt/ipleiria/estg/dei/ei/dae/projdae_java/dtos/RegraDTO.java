package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;

import java.util.List;

public class RegraDTO {
    @Id
    private long id;
    private int valor;
    private String comparador;
    private String mensagem;
    private String tipo_sensor;
    private long produto_id;
    public RegraDTO() {
    }

    public RegraDTO(long id, int valor, String comparador, String mensagem, String tipo_sensor,long produto_id) {
        this.id = id;
        this.valor = valor;
        this.comparador = comparador;
        this.mensagem = mensagem;
        this.tipo_sensor = tipo_sensor;
        this.produto_id = produto_id;
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

    public long getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(long produto_id) {
        this.produto_id = produto_id;
    }
}

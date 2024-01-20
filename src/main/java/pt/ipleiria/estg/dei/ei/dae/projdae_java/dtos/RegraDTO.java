package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;

public class RegraDTO {
    @Id
    private long id;
    private int valor;
    private String comparador;
    private String mensagem;
    private String tipoSensor;
    private long produtoId;

    public RegraDTO() {
    }

    public RegraDTO(long id, int valor, String comparador, String mensagem, String tipoSensor, long produtoId) {
        this.id = id;
        this.valor = valor;
        this.comparador = comparador;
        this.mensagem = mensagem;
        this.tipoSensor = tipoSensor;
        this.produtoId = produtoId;
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

    public long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }
}

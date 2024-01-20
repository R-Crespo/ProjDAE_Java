package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import java.util.Date;

public class ObservacaoDTO {
    private long id;
    private long sensorId;
    private Date timestamp;
    private String tipo;
    private double valor;
    private String unidadeMedida;

    // Construtores
    public ObservacaoDTO() {
    }

    public ObservacaoDTO(long id, long sensorId, Date timestamp, String tipo, double valor, String unidadeMedida) {
        this.id = id;
        this.sensorId = sensorId;
        this.timestamp = timestamp;
        this.tipo = tipo;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
    }

    // Getters e setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSensorId() {
        return sensorId;
    }

    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
}

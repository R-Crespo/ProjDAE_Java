package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "observacoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservacoes",
                query = "SELECT o FROM Observacao o ORDER BY o.id" // JPQL
        )
})
public class Observacao implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "sensor_nome")
    @NotNull
    private Sensor sensor;

    private Date timestamp;
    private String tipo;
    private double valor;
    private String unidadeMedida;

    public Observacao() {
    }

    public Observacao(Sensor sensor, Date timestamp, String tipo, double valor, String unidadeMedida) {
        this.sensor = sensor;
        this.timestamp = timestamp;
        this.tipo = tipo;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
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
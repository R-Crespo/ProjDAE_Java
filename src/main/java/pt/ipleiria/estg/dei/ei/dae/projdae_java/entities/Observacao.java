package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
@Table(name = "observacoes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllObservacoes",
                query = "SELECT o FROM Observacao o ORDER BY o.code" // JPQL
        )
})
public class Observacao implements Serializable {
    @Id
    private long code;
    @NotNull
    private Date timestamp;
    @NotNull
    private String type;
    @ManyToOne
    @JoinColumn(name = "sensor_name")
    @NotNull
    private Sensor sensor;
    @NotNull
    private long value;
    @NotNull
    private String unit;

    public Observacao() {
        this.code = 0;
        this.type = "";
        this.sensor = new Sensor();
        this.unit = "";
        this.value = 0;
        this.timestamp = new Date();
    }

    public Observacao(long code, String type, Sensor sensor, long value, String unit, Date timestamp) {
        this.code = code;
        this.type = type;
        this.sensor = sensor;
        this.value = value;
        this.unit = unit;
        this.timestamp = timestamp;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

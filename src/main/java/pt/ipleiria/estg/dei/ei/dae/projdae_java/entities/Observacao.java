package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
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
    private String description;
    @ManyToOne
    @JoinColumn(name = "sensor_name")
    @NotNull
    private Sensor sensor;

    public Observacao() {
        this.code = 0;
        this.description = "";
        this.sensor = new Sensor();
    }

    public Observacao(long code, String description, Sensor sensor) {
        this.code = code;
        this.description = description;
        this.sensor = sensor;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}

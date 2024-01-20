package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "embalagens")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagens",
                query = "SELECT e FROM Embalagem e ORDER BY e.id" // JPQL
        )
})
public class Embalagem implements Serializable {
    @Version
    private long version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String tipo;
    @NotNull
    private String funcao;
    @OneToMany(mappedBy = "embalagem" ,cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Sensor> sensores;
    private Date dataFabrico;
    @NotNull
    private String material;
    @NotNull
    private int peso;
    @NotNull
    private int volume;

    public Embalagem() {
        this.sensores = new ArrayList<Sensor>();
    }

    public Embalagem(String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) {
        this.tipo = tipo;
        this.funcao = funcao;
        this.dataFabrico = dataFabrico;
        this.material = material;
        this.peso = peso;
        this.volume = volume;
        this.sensores = new ArrayList<Sensor>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Date getDataFabrico() {
        return dataFabrico;
    }

    public void setDataFabrico(Date dataFabrico) {
        this.dataFabrico = dataFabrico;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public List<Sensor> getSensores() {
        return new ArrayList<>(sensores);
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
    }

    public void addSensor(Sensor sensor){
        if(sensor == null || sensores.contains(sensor)){
            return;
        }
        sensores.add(sensor);
    }

    public void removeSensor(Sensor sensor){
        if(sensor == null || !(sensores.contains(sensor))){
            return;
        }
        sensores.remove(sensor);
    }
}

package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Sensor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EmbalagemProdutoDTO {
    @Id
    private long id;
    private String tipo;
    private String funcao;
    private Date dataFabrico;
    private String material;
    private int peso;
    private int volume;
    private long produtoId;

    private List<SensorDTO> sensores;

    public EmbalagemProdutoDTO() {
        this.sensores = new ArrayList<>();
    }

    public EmbalagemProdutoDTO(long id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, long produto_id) {
        this.id = id;
        this.tipo = tipo;
        this.funcao = funcao;
        this.dataFabrico = dataFabrico;
        this.material = material;
        this.peso = peso;
        this.volume = volume;
        this.produtoId = produto_id;
        this.sensores = new ArrayList<>();
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

    public long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(long produto_id) {
        this.produtoId = produto_id;
    }

    public List<SensorDTO> getSensores() {
        return sensores;
    }

    public void setSensores(List<SensorDTO> sensores) {
        this.sensores = sensores;
    }
}

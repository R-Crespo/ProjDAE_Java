package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EncomendaDTO {
    @Id
    private long id;
    private String operadorUsername;
    private String clienteUsername;
    private String morada;
    private String estado;
    private Date dataEntrega;
    private String armazem;
    private List<EncomendaProdutoDTO> encomendaProdutoDTOs;

    private long embalagemTransporteId;
    private List<SensorDTO> sensoreDTOs;

    public EncomendaDTO() {
        encomendaProdutoDTOs = new ArrayList<EncomendaProdutoDTO>();
        sensoreDTOs = new ArrayList<SensorDTO>();
    }

    public EncomendaDTO(long id, String operadorUsername, String clienteUsarname, String morada, String estado, Date dataEntrega, String armazem, long embalagemTransporteId) {
        this.id = id;
        this.operadorUsername = operadorUsername;
        this.clienteUsername = clienteUsarname;
        this.morada = morada;
        this.estado = estado;
        this.dataEntrega = dataEntrega;
        this.armazem = armazem;
        this.encomendaProdutoDTOs = new ArrayList<EncomendaProdutoDTO>();
        this.embalagemTransporteId = embalagemTransporteId;
        this.sensoreDTOs = new ArrayList<SensorDTO>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperadorUsername() {
        return operadorUsername;
    }

    public void setOperadorUsername(String operadorUsername) {
        this.operadorUsername = operadorUsername;
    }

    public String getClienteUsername() {
        return clienteUsername;
    }

    public void setClienteUsername(String clienteUsername) {
        this.clienteUsername = clienteUsername;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(Date dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public String getArmazem() {
        return armazem;
    }

    public void setArmazem(String armazem) {
        this.armazem = armazem;
    }

    public List<EncomendaProdutoDTO> getEncomendaProdutoDTOs() {
        return new ArrayList<>(encomendaProdutoDTOs);
    }

    public void setEncomendaProdutoDTOs(List<EncomendaProdutoDTO> encomendaProdutoDTOs) {
        this.encomendaProdutoDTOs = encomendaProdutoDTOs;
    }

    public long getEmbalagemTransporteId() {
        return embalagemTransporteId;
    }

    public void setEmbalagemTransporteId(long embalagemTransporteId) {
        this.embalagemTransporteId = embalagemTransporteId;
    }

    public List<SensorDTO> getSensoreDTOs() {
        return new ArrayList<>(sensoreDTOs);
    }

    public void setSensoreDTOs(List<SensorDTO> sensoreDTOs) {
        this.sensoreDTOs = sensoreDTOs;
    }
}

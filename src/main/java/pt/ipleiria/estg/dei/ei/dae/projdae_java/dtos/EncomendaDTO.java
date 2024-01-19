package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EncomendaDTO {
    @Id
    private long id;
    private String operadorUsarname;
    private String clienteUsarname;
    private String morada;
    private String estado;
    private Date dataEntrega;
    private String armazem;
    private List<EncomendaProdutoDTO> encomendaProdutoDTOs;
    private List<EmbalagemTransporteDTO> embalagemTransporteDTOs;

    public EncomendaDTO() {
        encomendaProdutoDTOs = new ArrayList<EncomendaProdutoDTO>();
        embalagemTransporteDTOs = new ArrayList<EmbalagemTransporteDTO>();
    }

    public EncomendaDTO(long id, String operadorUsarname, String clienteUsarname, String morada, String estado, Date dataEntrega, String armazem) {
        this.id = id;
        this.operadorUsarname = operadorUsarname;
        this.clienteUsarname = clienteUsarname;
        this.morada = morada;
        this.estado = estado;
        this.dataEntrega = dataEntrega;
        this.armazem = armazem;
        encomendaProdutoDTOs = new ArrayList<EncomendaProdutoDTO>();
        embalagemTransporteDTOs = new ArrayList<EmbalagemTransporteDTO>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOperadorUsername() {
        return operadorUsarname;
    }

    public void setOperadorUsername(String operadorUsername) {
        this.operadorUsarname = operadorUsername;
    }

    public String getClienteUsername() {
        return clienteUsarname;
    }

    public void setClienteUsarname(String clienteUsarname) {
        this.clienteUsarname = clienteUsarname;
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

    public List<EmbalagemTransporteDTO> getEmbalagemTransporteDTOs() {
        return new ArrayList<>(embalagemTransporteDTOs);
    }

    public void setEmbalagemTransporteDTOs(List<EmbalagemTransporteDTO> embalagemTransporteDTOs) {
        this.embalagemTransporteDTOs = embalagemTransporteDTOs;
    }
}

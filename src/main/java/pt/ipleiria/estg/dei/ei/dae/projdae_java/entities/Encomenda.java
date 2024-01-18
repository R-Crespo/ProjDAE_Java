package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "encomendas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "Select e From Encomenda e JOIN FETCH e.encomendaProdutos JOIN FETCH e.embalagemTransportes Order By e.dataEntrega"
        )
}
)
public class Encomenda implements Serializable {
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "operador_username")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "cliente_username")
    @NotNull
    private Cliente cliente;
    @NotNull
    private String morada;
    @NotNull
    private String estado;
    @NotNull
    private Date dataEntrega;
    @NotNull
    private String armazem;
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EncomendaProduto> encomendaProdutos;
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EmbalagemTransporte> embalagemTransportes;

    public Encomenda() {
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.embalagemTransportes = new ArrayList<EmbalagemTransporte>();
    }

    public Encomenda(long id, Cliente cliente, String morada, String estado, String armazem, Date dataEntrega) {
        this.id = id;
        this.cliente = cliente;
        this.morada = morada;
        this.estado = estado;
        this.armazem = armazem;
        this.dataEntrega = dataEntrega;
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.embalagemTransportes = new ArrayList<EmbalagemTransporte>();
        this.operador = null;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<EncomendaProduto> getEncomendaProdutos() {
        return new ArrayList<>(encomendaProdutos);
    }

    public void setProdutos(List<EncomendaProduto> encomendaProdutos) {
        this.encomendaProdutos = encomendaProdutos;
    }

    public List<EmbalagemTransporte> getEmbalagemTransportes() {
        return new ArrayList<>(embalagemTransportes);
    }

    public void setEmbalagemTransportes(List<EmbalagemTransporte> embalagemTransportes) {
        this.embalagemTransportes = embalagemTransportes;
    }

    public void addEncomendaProduto(EncomendaProduto encomendaProduto){
        if(encomendaProduto == null || encomendaProdutos.contains(encomendaProduto)){
            return;
        }

        encomendaProdutos.add(encomendaProduto);
    }

    public void removeEncomendaProduto(EncomendaProduto encomendaProduto){
        if(encomendaProduto == null || !(encomendaProdutos.contains(encomendaProduto))){
            return;
        }

        encomendaProdutos.remove(encomendaProduto);
    }

    public void addEmbalagem(EmbalagemTransporte embalagemTransporte) {
        if(embalagemTransportes.contains(embalagemTransporte) || embalagemTransporte == null) {
            return;
        }

        embalagemTransportes.add(embalagemTransporte);
    }

    public void removeEmbalagem(EmbalagemTransporte embalagemTransporte) {
        if(!embalagemTransportes.contains(embalagemTransporte) || embalagemTransporte == null) {
            return;
        }

        embalagemTransportes.remove(embalagemTransporte);
    }
}

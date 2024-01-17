package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "encomendas")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "Select e From Encomenda e JOIN FETCH e.produtos JOIN FETCH e.embalagemTransportes Order By e.deliveryDate"
        )
}
)
public class Encomenda implements Serializable {
    @Id
    private long code;
    @ManyToOne
    @JoinColumn(name = "operador_username")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "cliente_username")
    @NotNull
    private Cliente cliente;
    @NotNull
    private String address;
    @NotNull
    private String state;
    @NotNull
    private Date deliveryDate;
    @NotNull
    private String warehouse;
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EmbalagemTransporte> embalagemTransportes = new ArrayList<>();

    public Encomenda() {
        this.code = 0;
        this.cliente = new Cliente();
        this.address = "";
        this.state = "";
        this.warehouse = "";
        this.deliveryDate = new Date();
    }

    public Encomenda(long code, Cliente cliente, String address, String state, String warehouse, Date deliveryDate) {
        this.code = code;
        this.cliente = cliente;
        this.address = address;
        this.state = state;
        this.warehouse = warehouse;
        this.deliveryDate = deliveryDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getState() {
        return state;
    }

    public void setState(String estado) {
        this.state = estado;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long codigo) {
        this.code = codigo;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String morada) {
        this.address = morada;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<EmbalagemTransporte> getEmbalagemTransportes() {
        return embalagemTransportes;
    }

    public void setEmbalagemTransportes(List<EmbalagemTransporte> embalagemTransportes) {
        this.embalagemTransportes = embalagemTransportes;
    }

    public void addProduto(Produto produto){
        if(produto == null || produtos.contains(produto)){
            return;
        }

        produtos.add(produto);
    }

    public void removeProduto(Produto produto){
        if(produto == null || !(produtos.contains(produto))){
            return;
        }

        produtos.remove(produto);
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

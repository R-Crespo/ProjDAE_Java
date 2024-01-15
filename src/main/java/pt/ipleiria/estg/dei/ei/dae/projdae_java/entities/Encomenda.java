package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEncomendas",
                query = "Select e From Encomenda e JOIN FETCH e.produtos JOIN FETCH e.embalagemTransportes Order By e.code"
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
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Produto> produtos = new ArrayList<>();
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "encomendas_embalagens",
            joinColumns = @JoinColumn(
                    name = "encomenda_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_code",
                    referencedColumnName = "code"
            )
    )
    @NotNull
    private List<EmbalagemTransporte> embalagemTransportes = new ArrayList<>();

    public Encomenda() {

    }

    public Encomenda(long code, Cliente cliente, String address, String state) {
        this.code = code;
        this.cliente = cliente;
        this.address = address;
        this.state = state;
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

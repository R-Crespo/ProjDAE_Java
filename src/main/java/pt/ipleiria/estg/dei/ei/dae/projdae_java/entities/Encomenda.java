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
    private long codigo;
    @ManyToOne
    @JoinColumn(name = "operator_username")
    private Operador operador;
    @ManyToOne
    @JoinColumn(name = "client_username")
    @NotNull
    private Cliente cliente;
    @NotNull
    private String morada;
    @NotNull
    private String estado;
    @OneToMany(mappedBy = "encomenda", cascade = CascadeType.REMOVE)
    private List<Produto> produtos = new ArrayList<>();
    @ManyToMany
    @JoinTable(
            name = "encomendas_embalagens",
            joinColumns = @JoinColumn(
                    name = "encomenda_codigo",
                    referencedColumnName = "codigo"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_codigo",
                    referencedColumnName = "codigo"
            )
    )
    private List<EmbalagemTransporte> embalagemTransportes = new ArrayList<>();

    public Encomenda() {

    }

    public Encomenda(long codigo, Cliente cliente, String morada, String estado) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.morada = morada;
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
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

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
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

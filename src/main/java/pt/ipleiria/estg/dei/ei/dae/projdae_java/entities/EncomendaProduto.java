package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(
        name="produtoQuantidades",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
)
public class ProdutoQuantidade{
    @Id
    private long id;
    @ManyToOne
    @JoinColumn(name = "encomenda_id")
    private Encomenda encomenda;
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;
    private int quantidade;

    public ProdutoQuantidade() {
    }

    public ProdutoQuantidade(long id, Encomenda encomenda, Produto produto, int quantidade) {
        this.id = id;
        this.encomenda = encomenda;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}


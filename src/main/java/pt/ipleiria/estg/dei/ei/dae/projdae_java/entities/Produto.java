package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.name" // JPQL
        )
})
public class Produto implements Serializable {
    @Id
    private long code;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private float price;
    private String description;
    @ManyToOne
    @JoinColumn(name = "encomenda_code")
    private Encomenda encomenda;

    @ManyToOne
    @JoinColumn(name = "fornecedor_username")
    @NotNull
    private Fornecedor fornecedor;

    @ManyToMany
    @JoinTable(
            name = "produto_embalagens",
            joinColumns = @JoinColumn(
                    name = "produto_code",
                    referencedColumnName = "code"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "embalagem_code",
                    referencedColumnName = "codigo"
            )
    )
    private List<EmbalagemProduto> embalagensProduto = new ArrayList<>();

    public Produto(){
    }

    public Produto(long code,String name, String type, float price, String description, Fornecedor fornecedor) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.fornecedor = fornecedor;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public List<EmbalagemProduto> getEmbalagensProduto() {
        return embalagensProduto;
    }

    public void setEmbalagensProduto(List<EmbalagemProduto> embalagensProduto) {
        this.embalagensProduto = embalagensProduto;
    }
}

package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p JOIN FETCH p.embalagensProduto ORDER BY p.name" // JPQL
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
    private String brand;
    @NotNull
    private long quantity;
    @NotNull
    private String measure;
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

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<EmbalagemProduto> embalagensProduto = new ArrayList<>();

    public Produto(){
        this.code = 0;
        this.name = "";
        this.type = "";
        this.price = 00.00f;
        this.description = "";
        this.fornecedor = new Fornecedor();
        this.brand = "";
        this.quantity = 0;
        this.measure = "";
    }

    public Produto(long code,String name, String type, float price, String description, Fornecedor fornecedor, String brand, long quantity, String measure) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
        this.fornecedor = fornecedor;
        this.quantity = quantity;
        this.measure = measure;
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
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

    public void addEmbalagemProduto(EmbalagemProduto embalagemProduto){
        if(embalagemProduto == null || embalagensProduto.contains(embalagemProduto)){
            return;
        }
        embalagemProduto.setProduto(this);
        embalagensProduto.add(embalagemProduto);
    }

    public void removeEmbalagemProduto(EmbalagemProduto embalagemProduto){
        if(embalagemProduto == null || !(embalagensProduto.contains(embalagemProduto))){
            return;
        }
        embalagemProduto.setProduto(null);
        embalagensProduto.remove(embalagemProduto);
    }
}

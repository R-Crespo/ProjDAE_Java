package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p LEFT JOIN FETCH p.encomendaProdutos ORDER BY p.nome" // JPQL
        )
})
public class Produto implements Serializable {
    @Version
    private long version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String nome;
    @NotNull
    private String tipo;
    @NotNull
    private String marca;
    @NotNull
    private long quantidade;
    @NotNull
    private String unidadeMedida;
    @NotNull
    private float preco;
    private String descricao;
    @OneToMany(mappedBy = "produto", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<EncomendaProduto> encomendaProdutos;

    @OneToOne
    private EmbalagemProduto embalagemProduto;

    @OneToMany(mappedBy = "produto", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Regra> regras;

    public Produto(){
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.regras = new ArrayList<Regra>();
    }

    public Produto(String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao) {
        this.nome = nome;
        this.tipo = tipo;
        this.marca = marca;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.preco = preco;
        this.descricao = descricao;
        this.encomendaProdutos = new ArrayList<EncomendaProduto>();
        this.embalagemProduto = null;
        this.regras = new ArrayList<Regra>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<EncomendaProduto> getEncomendaProduto() {
        return new ArrayList<>(encomendaProdutos);
    }

    public void setEncomendaProduto(List<EncomendaProduto> encomendaProduto) {
        this.encomendaProdutos = encomendaProduto;
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

    public List<Regra> getRegras() {
        return regras;
    }

    public void setRegras(List<Regra> regras) {
        this.regras = regras;
    }

    public void addRegra(Regra regra){
        if(regra == null || regras.contains(regra)){
            return;
        }
        regras.add(regra);
    }

    public void removeRegra(Regra regra){
        if(regra == null || !(regras.contains(regra))){
            return;
        }
        regras.remove(regra);
    }

    public List<EncomendaProduto> getEncomendaProdutos() {
        return encomendaProdutos;
    }

    public EmbalagemProduto getEmbalagemProduto() {
        return embalagemProduto;
    }

    public void setEmbalagemProduto(EmbalagemProduto embalagemProduto) {
        this.embalagemProduto = embalagemProduto;
    }
}

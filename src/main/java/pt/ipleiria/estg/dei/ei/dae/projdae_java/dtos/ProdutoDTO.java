package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.EncomendaProduto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Regra;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDTO {
    @Id
    private long id;
    private String nome;
    private String marca;
    private String tipo;
    private String descricao;
    private long quantidade;
    private String unidadeMedida;
    private float preco;
    private long embalagemProdutoId;
    private List<EncomendaProdutoDTO> encomendaProdutos;
    private List<RegraDTO> regras;

    public ProdutoDTO() {
        this.encomendaProdutos = new ArrayList<>();
        this.regras = new ArrayList<>();
    }

    public ProdutoDTO(long id, String nome, String tipo ,String marca, String descricao, long quantidade, String unidadeMedida, float preco, List<RegraDTO> regras, long embalagemProdutoId) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.marca = marca;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.unidadeMedida = unidadeMedida;
        this.preco = preco;
        this.regras = regras;
        this.embalagemProdutoId = embalagemProdutoId;
    }

    public ProdutoDTO(long id, String nome, List<EncomendaProdutoDTO> encomendas) {
        this.id = id;
        this.nome = nome;
        this.encomendaProdutos = encomendas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public long getEmbalagemProdutoId() {
        return embalagemProdutoId;
    }

    public void setEmbalagemProdutoId(long embalagemProdutoId) {
        this.embalagemProdutoId = embalagemProdutoId;
    }

    public List<EncomendaProdutoDTO> getEncomendaProdutos() {
        return encomendaProdutos;
    }

    public void setEncomendaProdutos(List<EncomendaProdutoDTO> encomendaProdutos) {
        this.encomendaProdutos = encomendaProdutos;
    }

    public List<RegraDTO> getRegras() {
        return regras;
    }

    public void setRegras(List<RegraDTO> regras) {
        this.regras = regras;
    }
}

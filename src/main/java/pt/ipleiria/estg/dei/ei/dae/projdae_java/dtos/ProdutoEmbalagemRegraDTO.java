package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Regra;

import java.util.List;

public class ProdutoEmbalagemRegraDTO {
    private ProdutoDTO produto;
    private EmbalagemProdutoDTO embalagemProduto;
    private List<RegraDTO> regras;

    public ProdutoEmbalagemRegraDTO(ProdutoDTO produto, EmbalagemProdutoDTO embalagemProduto, List<RegraDTO> regras) {
        this.produto = produto;
        this.embalagemProduto = embalagemProduto;
        this.regras = regras;
    }

    public ProdutoEmbalagemRegraDTO() {
    }

    public ProdutoDTO getProduto() {
        return produto;
    }

    public void setProduto(ProdutoDTO produto) {
        this.produto = produto;
    }

    public EmbalagemProdutoDTO getEmbalagemProduto() {
        return embalagemProduto;
    }

    public void setEmbalagemProduto(EmbalagemProdutoDTO embalagemProduto) {
        this.embalagemProduto = embalagemProduto;
    }

    public List<RegraDTO> getRegras() {
        return regras;
    }

    public void setRegras(List<RegraDTO> regras) {
        this.regras = regras;
    }
}

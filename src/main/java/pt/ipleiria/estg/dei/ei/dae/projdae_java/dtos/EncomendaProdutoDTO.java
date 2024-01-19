package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;

public class EncomendaProdutoDTO {
    @Id
    private long id;
    private long encomendaId;
    private long produtoId;
    private int quantidade;

    public EncomendaProdutoDTO() {
    }

    public EncomendaProdutoDTO(long id, long encomendaId, long produtoId, int quantidade) {
        this.id = id;
        this.encomendaId = encomendaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEncomendaId() {
        return encomendaId;
    }

    public void setEncomendaId(long encomendaId) {
        this.encomendaId = encomendaId;
    }

    public long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(
        name="embalagens",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id"})
)
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagensProduto",
                query = "SELECT DISTINCT e FROM EmbalagemProduto e LEFT JOIN FETCH e.produto ORDER BY e.id"
        )
})
public class EmbalagemProduto extends Embalagem{
    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    public EmbalagemProduto() {
    }

    public EmbalagemProduto(long id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, Produto produto) {
        super(id, tipo, funcao, dataFabrico, material, peso, volume);
        this.produto = produto;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}

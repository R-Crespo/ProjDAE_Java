package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;

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
                query = "SELECT DISTINCT e FROM EmbalagemProduto e LEFT JOIN FETCH e.produtos ORDER BY e.id"
        )
})
public class EmbalagemProduto extends Embalagem{
    @ManyToMany(mappedBy = "produtos")
    /*
    @ManyToMany
    @JoinTable(
        name = "embalagensProduto_produtos",
        joinColumns = @JoinColumn(
            name = "produto_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "embalagemProduto_id",
            referencedColumnName = "id"
        )
    */
    private List<Produto> produtos;

    public EmbalagemProduto() {
        super(0, "", "", null, "", 0, 0);
        this.produtos = new ArrayList<>();
    }

    public EmbalagemProduto(int id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) {
        super(id, tipo, funcao, dataFabrico, material, peso, volume);
        this.produtos = new ArrayList<>();
    }

    public List<Produto> getProdutos()  {
        return new ArrayList<>(produtos);
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}

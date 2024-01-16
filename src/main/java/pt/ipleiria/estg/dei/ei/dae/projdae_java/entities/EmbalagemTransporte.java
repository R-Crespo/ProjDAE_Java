package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagensTransporte",
                query = "SELECT DISTINCT e FROM EmbalagemTransporte e LEFT JOIN FETCH e.encomenda ORDER BY e.id"
        )
})
public class EmbalagemTransporte extends Embalagem implements Serializable {
    @ManyToOne
    @JoinColumn(name = "encomenda_code")
    @NotNull
    private Encomenda encomenda;

    public EmbalagemTransporte() {
        super(0, "", "", null, "", 0, 0);
        encomenda = new Encomenda();
    }

    public EmbalagemTransporte(int id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, Encomenda encomenda) {
        super(id, tipo, funcao, dataFabrico, material, peso, volume);
        this.encomenda = encomenda;
    }

    public Encomenda getEncomenda() {
        return encomenda;
    }

    public void setEncomenda(Encomenda encomenda) {
        this.encomenda = encomenda;
    }
}

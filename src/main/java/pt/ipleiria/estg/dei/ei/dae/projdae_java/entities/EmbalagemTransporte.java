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
                query = "SELECT DISTINCT e FROM EmbalagemTransporte e ORDER BY e.id"
        )
})
public class EmbalagemTransporte extends Embalagem implements Serializable {

    @OneToMany(mappedBy = "embalagemTransporte", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Encomenda> encomendas;

    public EmbalagemTransporte() {
    }

    public EmbalagemTransporte(String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) {
        super(tipo, funcao, dataFabrico, material, peso, volume);
        this.encomendas = new ArrayList<Encomenda>();
    }

    public List<Encomenda> getEncomendas() {
        return encomendas;
    }

    public void addEncomenda(Encomenda encomenda){
        if(encomenda == null || encomendas.contains(encomenda)){
            return;
        }
        encomendas.add(encomenda);
    }

    public void removeEncomenda(Encomenda encomenda){
        if(encomenda == null || !encomendas.contains(encomenda)){
            return;
        }
        encomendas.remove(encomenda);
    }
}

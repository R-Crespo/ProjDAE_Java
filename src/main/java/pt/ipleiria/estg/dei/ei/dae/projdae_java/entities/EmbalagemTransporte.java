package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllEmbalagensTransporte",
                query = "SELECT DISTINCT e FROM EmbalagemTransporte e LEFT JOIN FETCH e.encomendas ORDER BY e.id"
        )
})
public class EmbalagemTransporte extends Embalagem implements Serializable {
    @ManyToMany(mappedBy = "encomendas")
    /*
    @ManyToMany
    @JoinTable(
        name = "embalagensTransporte_encomendas",
        joinColumns = @JoinColumn(
            name = "encomenda_id",
            referencedColumnName = "id"
        ),
        inverseJoinColumns = @JoinColumn(
            name = "embalagemTransporte_id",
            referencedColumnName = "id"
        )
    */
    private List<Encomenda> encomendas;

    public EmbalagemTransporte() {
        super(0, "", "", null, "", 0, 0);
        this.encomendas = new ArrayList<>();
    }

    public EmbalagemTransporte(int id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) {
        super(id, tipo, funcao, dataFabrico, material, peso, volume);
        this.encomendas = new ArrayList<>();
    }

    public List<Encomenda> getEncomendas()  {
        return new ArrayList<>(encomendas);
    }

    public void setEncomendas(List<Encomenda> encomendas) {
        this.encomendas = encomendas;
    }

    public void addEncomenda(Encomenda encomenda){
        this.encomendas.add(encomenda);
    }
    public void removeEncomenda(Encomenda encomenda){
        this.encomendas.remove(encomenda);
    }
}

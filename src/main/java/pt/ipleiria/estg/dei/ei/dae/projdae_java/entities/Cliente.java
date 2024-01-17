package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClientes",
                query = "SELECT DISTINCT c FROM Cliente c LEFT JOIN FETCH c.encomendas ORDER BY c.nome"
        )
})
public class Cliente extends User{

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Encomenda> encomendas;

    public Cliente() {
        this.encomendas = new ArrayList<Encomenda>();
    }

    public Cliente (String username, String password, String name, String email){
        super(username, password,name, email);
        this.encomendas = new ArrayList<Encomenda>();
    }

    public List<Encomenda> getEncomendas() {
        return new ArrayList<>(encomendas);
    }

    public void addEncomenda(Encomenda encomenda){
        if(encomenda == null){
            return;
        }
        if(encomendas.contains(encomenda)){
            return;
        }
        encomendas.add(encomenda);
    }

    public void removeEncomenda(Encomenda encomenda){
        if(encomenda == null){
            return;
        }
        if(!(encomendas.contains(encomenda))){
            return;
        }
        encomendas.remove(encomenda);
    }

}

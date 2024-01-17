package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Operador extends User{
    @OneToMany(mappedBy = "operador", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Encomenda> encomendas;

    public Operador() {
        this.encomendas = new ArrayList<Encomenda>();
    }

    public Operador (String username, String password, String name, String email){
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

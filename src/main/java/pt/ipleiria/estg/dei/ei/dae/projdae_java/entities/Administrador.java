package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Administrador extends User{

    public Administrador() {

    }

    public Administrador(String username, String password, String name, String email){
        super(username, password,name, email);
    }
}

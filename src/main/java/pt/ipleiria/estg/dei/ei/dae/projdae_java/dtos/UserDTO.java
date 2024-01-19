package pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos;

import jakarta.persistence.Id;

public class UserDTO {
    @Id
    private String username;

    private String nome;

    private String tipo;


    public UserDTO(String username, String nome, String tipo) {
        this.username = username;
        this.nome = nome;
        this.tipo = tipo;
    }

    public UserDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}

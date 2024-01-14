package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.io.Serializable;
@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProdutos",
                query = "SELECT p FROM Produto p ORDER BY p.name" // JPQL
        )
})
public class Produto implements Serializable {
    @Id
    private long code;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    private float price;
    private String description;

    public Produto(){
    }

    public Produto(long code,String name, String type, float price, String description) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

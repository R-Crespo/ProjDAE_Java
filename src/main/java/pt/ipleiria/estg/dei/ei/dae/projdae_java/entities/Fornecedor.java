package pt.ipleiria.estg.dei.ei.dae.projdae_java.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fornecedor extends User{
    @OneToMany(mappedBy = "fornecedor", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Produto> produtos;

    public Fornecedor() {
        super("","","","");
        this.produtos = new ArrayList<>();
    }

    public Fornecedor (String username, String password, String name, String email){
        super(username, password,name, email);
        this.produtos = new ArrayList<>();
    }

    public List<Produto> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public void addProduto(Produto produto){
        if(produto == null){
            return;
        }
        if(produtos.contains(produto)){
            return;
        }
        produtos.add(produto);
    }

    public void removeProduto(Produto produto){
        if(produto == null){
            return;
        }
        if(!produtos.contains(produto)){
            return;
        }
        produtos.remove(produto);
    }
}

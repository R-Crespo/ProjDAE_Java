package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoBean {

    @PersistenceContext
    private EntityManager em;

    public Produto find(long code){
        return em.find(Produto.class, code);
    }

    public List<Produto> getAll() {
        return em.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public void create(long code,String name, String type, float price, String description) throws MyEntityExistsException{
        Produto produto = find(code);
        if(produto != null){
            throw new MyEntityExistsException("Produto with code '"+ code +"' already exists");
        }
        produto = new Produto(code, name, type, price, description);
        em.persist(produto);
    }

    public void update(long code, String name, String type,
                       float price, String description) throws MyEntityNotFoundException {
        Produto produto = em.find(Produto.class, code);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto with code '" + code + "' not found");
        }
        em.lock(produto, LockModeType.OPTIMISTIC);
        produto.setName(name);
        produto.setType(type);
        produto.setPrice(price);
        produto.setDescription(description);
    }

    public void delete(long code) throws  MyEntityNotFoundException{
        Produto produto = em.find(Produto.class, code);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto with code '" + code + "' not found");
        }

        em.remove(produto);
    }
}

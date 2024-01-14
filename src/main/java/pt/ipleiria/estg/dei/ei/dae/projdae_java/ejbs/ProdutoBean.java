package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Encomenda;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Fornecedor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Produto;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProdutoBean {

    @PersistenceContext
    private EntityManager em;

    private EncomendaBean encomendaBean;

    private FornecedorBean fornecedorBean;

    public Produto find(long code){
        return em.find(Produto.class, code);
    }

    public List<Produto> getAll() {
        return em.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public void create(long code,String name, String type, float price, String description, String fornecedorUsername) throws MyEntityNotFoundException,MyEntityExistsException{
        Produto produto = find(code);
        if(produto != null){
            throw new MyEntityExistsException("Produto with code '"+ code +"' already exists");
        }
        Fornecedor fornecedor = fornecedorBean.find(fornecedorUsername);
        if(fornecedor == null){
            throw new MyEntityNotFoundException(
                    "Fornecedor '" + fornecedorUsername + "' não existe");
        }
        produto = new Produto(code, name, type, price, description, fornecedor);
        em.persist(produto);
    }

    public void update(long code, String name, String type,
                       float price, String description, Long encomendaCode, String fornecedorUsername) throws MyEntityNotFoundException {
        Produto produto = em.find(Produto.class, code);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto with code '" + code + "' not found");
        }
        em.lock(produto, LockModeType.OPTIMISTIC);
        produto.setName(name);
        produto.setType(type);
        produto.setPrice(price);
        produto.setDescription(description);
        Encomenda encomenda = encomendaBean.find(encomendaCode);
        if(encomenda == null & encomendaCode != null){
            throw new MyEntityNotFoundException(
                    "Encomenda '" + encomendaCode + "' não existe");
        }
        Fornecedor fornecedor = fornecedorBean.find(fornecedorUsername);
        if(fornecedor == null){
            throw new MyEntityNotFoundException(
                    "Fornecedor '" + fornecedorUsername + "' não existe");
        }
        produto.setEncomenda(encomenda);
    }

    public void delete(long code) throws  MyEntityNotFoundException,MyConstraintViolationException {
        try {
            Produto produto = em.find(Produto.class, code);
            if (produto == null) {
                throw new MyEntityNotFoundException("Produto with code '" + code + "' not found");
            }

            em.remove(produto);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }
}

package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
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

    public Produto find(long id){
        return em.find(Produto.class, id);
    }

    public List<Produto> getAll() {
        return em.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public void create(long id, String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao, String fornecedorUsername) throws MyEntityNotFoundException,MyEntityExistsException{
        Produto produto = find(id);
        if(produto != null){
            throw new MyEntityExistsException("Produto com id '"+ id +"' já existe");
        }
        Fornecedor fornecedor = fornecedorBean.find(fornecedorUsername);
        if(fornecedor == null){
            throw new MyEntityNotFoundException(
                    "Fornecedor '" + fornecedorUsername + "' não existe");
        }

        produto = new Produto(id, nome, tipo, marca, quantidade, unidadeMedida, preco, descricao, fornecedor);
        fornecedor.addProduto(produto);
        em.persist(produto);
    }

    public void update(long id, String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao, String fornecedorUsername) throws MyEntityNotFoundException {
        Produto produto = em.find(Produto.class, id);
        if (produto == null) {
            throw new MyEntityNotFoundException("Produto com id '" + id + "' não existe");
        }
        em.lock(produto, LockModeType.OPTIMISTIC);
        produto.setNome(nome);
        produto.setTipo(tipo);
        produto.setPreco(preco);
        produto.setDescricao(descricao);
        produto.setMarca(marca);
        produto.setUnidadeMedida(unidadeMedida);
        produto.setQuantidade(quantidade);

        Fornecedor fornecedor = fornecedorBean.find(fornecedorUsername);
        if(fornecedor == null){
            throw new MyEntityNotFoundException(
                    "Fornecedor '" + fornecedorUsername + "' não existe");
        }
        if(fornecedor != produto.getFornecedor()){
            produto.getFornecedor().removeProduto(produto);
            fornecedor.addProduto(produto);
            produto.setFornecedor(fornecedor);
        }

    }

    public void delete(long id) throws  MyEntityNotFoundException,MyConstraintViolationException {
        try {
            Produto produto = em.find(Produto.class, id);
            if (produto == null) {
                throw new MyEntityNotFoundException("Produto com id '" + id + "' não existe");
            }
            produto.getFornecedor().removeProduto(produto);
            if(produto.getEmbalagensProduto() != null){
                produto.getEmbalagensProduto().clear();
            }
            for (EmbalagemProduto embalagemProduto : produto.getEmbalagensProduto()) {
                embalagemProduto.setProduto(null);
            }
            em.remove(produto);
        }catch(ConstraintViolationException e){
            throw new MyConstraintViolationException(e);
        }
    }


}

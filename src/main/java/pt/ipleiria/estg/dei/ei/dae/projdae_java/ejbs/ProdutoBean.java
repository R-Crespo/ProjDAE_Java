package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.Hibernate;
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

    private AdministratorBean administratorBean;

    public Produto find(long id){
        Produto produto = em.find(Produto.class, id);
        if(produto != null){
            Hibernate.initialize(produto.getEncomendaProdutos());
        }
        return produto;
    }

    public List<Produto> getAll() {
        return em.createNamedQuery("getAllProdutos", Produto.class).getResultList();
    }

    public Produto create(String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao) throws MyEntityNotFoundException,MyEntityExistsException{
        Produto produto = new Produto(nome, tipo, marca, quantidade, unidadeMedida, preco, descricao);
        em.persist(produto);
        em.flush();
        return produto;
    }

    public void update(long id, String nome, String tipo, String marca, long quantidade, String unidadeMedida, float preco, String descricao, Long regraId, Long embalagemProdutoId) throws MyEntityNotFoundException {
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
        if(embalagemProdutoId != null){
            EmbalagemProduto embalagemProduto = em.find(EmbalagemProduto.class, embalagemProdutoId);
            produto.setEmbalagemProduto(embalagemProduto);
        }
        if(regraId != null) {
            Regra regra = em.find(Regra.class, regraId);
            produto.addRegra(regra);
        }
    }

    public void delete(long id) throws MyEntityNotFoundException, MyConstraintViolationException, MyEntityExistsException {
        try {
            Produto produto = em.find(Produto.class, id);
            if (produto == null) {
                throw new MyEntityNotFoundException("Produto com id '" + id + "' não existe");
            }
            em.remove(produto.getEmbalagemProduto());
            List<Regra> regras = produto.getRegras();
            if (regras != null) {
                for (Regra regra : regras) {
                    em.remove(regra);
                }
                regras.clear();
            }

                if (!produto.getEncomendaProdutos().isEmpty()) {
                    throw new MyEntityExistsException("Produto com id '" + id + "' não pode ser apagado pois existem encomendas relacionadas");
                }

                for (Sensor sensor : produto.getEmbalagemProduto().getSensores()) {
                    em.remove(sensor);
                }
                em.remove(produto.getEmbalagemProduto());
                em.remove(produto);
            }catch(ConstraintViolationException e){
                throw new MyConstraintViolationException(e);
            }
        }
    }

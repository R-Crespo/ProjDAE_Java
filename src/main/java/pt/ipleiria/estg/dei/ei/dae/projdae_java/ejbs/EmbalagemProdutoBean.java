package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.dtos.EmbalagemProdutoDTO;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;

@Stateless
public class EmbalagemProdutoBean {
    @PersistenceContext
    private EntityManager em;
    private ProdutoBean produtoBean;

    public EmbalagemProduto find(long id) {
        return em.find(EmbalagemProduto.class, id);
    }

    public EmbalagemProduto getEmbalagemProduto(){
        List<EmbalagemProduto> embalagens = em.createNamedQuery("getAllEmbalagensProduto", EmbalagemProduto.class).getResultList();
        if (!embalagens.isEmpty()) {
            return embalagens.get(0);
        } else {
            return new EmbalagemProduto();
        }
    }

    public List<EmbalagemProduto> getAll(){
        return em.createNamedQuery("getAllEmbalagensProduto", EmbalagemProduto.class).getResultList();
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(e.id) FROM EmbalagemProduto e WHERE e.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public EmbalagemProduto create(String tipo, String funcao, Date dataFabrico, String material, int peso, int volume, Produto produto) throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        EmbalagemProduto embalagemProduto = null;
        produto = em.merge(produto);
        try {
            embalagemProduto = new EmbalagemProduto(tipo, funcao, dataFabrico, material, peso, volume, produto);
            produto.setEmbalagemProduto(embalagemProduto);
            em.persist(embalagemProduto);
            em.flush();
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        return embalagemProduto;
    }

    public void update(long id, String tipo, String funcao, Date dataFabrico, String material, int peso, int volume) throws  MyEntityNotFoundException{
        EmbalagemProduto embalagemProduto = em.find(EmbalagemProduto.class, id);
        if (embalagemProduto == null) {
            throw new MyEntityNotFoundException("Embalagem de Produto com id '" + id + "' não existe");
        }
        em.lock(embalagemProduto, LockModeType.OPTIMISTIC);
        embalagemProduto.setTipo(tipo);
        embalagemProduto.setFuncao(funcao);
        embalagemProduto.setDataFabrico(dataFabrico);
        embalagemProduto.setMaterial(material);
        embalagemProduto.setPeso(peso);
        embalagemProduto.setVolume(volume);
    }
}

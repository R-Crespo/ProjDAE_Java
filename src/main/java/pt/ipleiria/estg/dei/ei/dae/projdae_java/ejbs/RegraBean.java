package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.validation.ConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.*;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityNotFoundException;

import java.util.Date;
import java.util.List;
@Stateless
public class RegraBean {
    @PersistenceContext
    private EntityManager em;

    public Regra find(long id) {
        return em.find(Regra.class, id);
    }

    public List<Regra> getAll(){
        return em.createNamedQuery("getAllRegras", Regra.class).getResultList();
    }

    public List<Regra> getAllRegrasProduto(long produtoId) {
        return em.createNamedQuery("getAllRegrasProduto", Regra.class).setParameter("produtoId", produtoId).getResultList();
    }

    public boolean exists(long id) {
        Query query = em.createQuery(
                "SELECT COUNT(r.id) FROM Regra r WHERE r.id = :id",
                Long.class
        );
        query.setParameter("id", id);
        return (Long)query.getSingleResult() > 0L;
    }

    public Regra create(int valor, String comparador, String mensagem, String tipoSensor, long produtoId) throws MyEntityExistsException, MyConstraintViolationException, MyEntityNotFoundException {
        Produto produto = em.find(Produto.class, produtoId);
        if (produto == null) {
            throw new MyEntityNotFoundException(
                    "Produto '" + produtoId + "' não existe");
        }

        Regra regra = null;
        try {
            regra = new Regra(valor, comparador, mensagem, tipoSensor, produto);
            produto.addRegra(regra);
            em.persist(regra);
        } catch (ConstraintViolationException e) {
            throw new MyConstraintViolationException(e);
        }
        return regra;
    }

    public void update(long id, int valor, String comparador, String mensagem, String tipoSensro) throws MyEntityNotFoundException {
        Regra regra = find(id);
        if (regra == null) {
            throw new MyEntityNotFoundException("Regra com id '" + id + "' não existe");
        }

        em.lock(regra, LockModeType.OPTIMISTIC);


        regra.setValor(valor);
        regra.setComparador(comparador);
        regra.setMensagem(mensagem);
        regra.setTipoSensor(tipoSensro);

    }

    public Regra delete(long id) throws MyEntityNotFoundException {
        Regra regra = find(id);
        if (regra == null) {
            throw new MyEntityNotFoundException("Regra com id '" + id + "' não existe");
        }

        regra.getProduto().removeRegra(regra);

        em.remove(regra);
        return regra;
    }
}

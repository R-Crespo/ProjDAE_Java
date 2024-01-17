package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Operador;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Hasher;

@Stateless
public class OperadorBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public Operador find(String username) {
        Operador operador = em.find(Operador.class, username);
        if (operador != null) {
            Hibernate.initialize(operador.getEncomendas());
        }
        return operador;
    }

    public void create(String username, String password, String nome, String email) throws MyEntityExistsException {
        Operador operador = find(username);

        if (operador != null) {
            throw new MyEntityExistsException(
                    "Operador com o username '" + username + "' ja existe");
        }

        operador = new Operador(username, hasher.hash(password), nome, email);
        em.persist(operador);
    }
}

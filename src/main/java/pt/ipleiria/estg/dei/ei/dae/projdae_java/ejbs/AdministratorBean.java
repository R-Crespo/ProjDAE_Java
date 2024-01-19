package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;

import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Administrador;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Hasher;

@Stateless
public class AdministratorBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public Administrador find(String username) {
        return em.find(Administrador.class, username);
    }

    public void create(String username, String password, String nome, String email) throws MyEntityExistsException {
        Administrador administrador = find(username);

        if (administrador != null) {
            throw new MyEntityExistsException("Administrador com o username '" + username + "' ja existe");
        }

        administrador = new Administrador(username, hasher.hash(password), nome, email);
        em.persist(administrador);
    }
}

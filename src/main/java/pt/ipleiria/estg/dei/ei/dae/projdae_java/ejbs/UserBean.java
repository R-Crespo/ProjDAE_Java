package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.User;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Hasher;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public User find(String username) {
        return em.find(User.class, username);
    }

    public User findOrFail(String username) {
        User user = em.getReference(User.class, username);
        Hibernate.initialize(user);
        return user;
    }

    public boolean canLogin(String username, String password) {
        User user = find(username);
        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}

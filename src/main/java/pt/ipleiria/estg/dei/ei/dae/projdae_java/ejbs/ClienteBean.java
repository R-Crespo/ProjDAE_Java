package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Cliente;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Hasher;

@Stateless
public class ClienteBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public Cliente find(String username) {
        Cliente cliente = em.find(Cliente.class, username);
        if (cliente != null) {
            Hibernate.initialize(cliente.getEncomendas());
        }
        return cliente;
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException {
        Cliente cliente = find(username);

        if (cliente != null) {
            throw new MyEntityExistsException(
                    "Cliente com o username '" + username + "' ja existe");
        }

        cliente = new Cliente(username, hasher.hash(password), name, email);
        em.persist(cliente);
    }
}

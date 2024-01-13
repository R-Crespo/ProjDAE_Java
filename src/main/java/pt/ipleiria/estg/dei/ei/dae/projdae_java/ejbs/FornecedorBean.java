package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;

import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Fornecedor;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.exceptions.MyEntityExistsException;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.security.Hasher;

@Stateless
public class FornecedorBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;

    public Fornecedor find(String username) {
        Fornecedor fornecedor = em.find(Fornecedor.class, username);
        if (fornecedor != null) {
            Hibernate.initialize(fornecedor.getProdutos());
        }
        return fornecedor;
    }

    public void create(String username, String password, String name, String email) throws MyEntityExistsException {
        Fornecedor fornecedor = find(username);

        if (fornecedor != null) {
            throw new MyEntityExistsException(
                    "Fornecedor com o username '" + username + "' ja existe");
        }

        fornecedor = new Fornecedor(username, hasher.hash(password), name, email);
        em.persist(fornecedor);
    }
}

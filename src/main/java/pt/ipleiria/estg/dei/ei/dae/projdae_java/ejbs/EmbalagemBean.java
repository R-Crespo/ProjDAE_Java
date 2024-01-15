package pt.ipleiria.estg.dei.ei.dae.projdae_java.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.projdae_java.entities.Embalagem;

import java.util.List;

@Stateless
public class EmbalagemBean {
    @PersistenceContext
    private EntityManager em;

    public Embalagem find(String id) {
        return em.find(Embalagem.class, id);
    }

    public List<Embalagem> getAll(){
        return em.createNamedQuery("getAllEmbalagens", Embalagem.class).getResultList();
    }
}

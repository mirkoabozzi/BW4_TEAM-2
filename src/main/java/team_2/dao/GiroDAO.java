package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Giro;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class GiroDAO {
    private final EntityManager em;

    public GiroDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Giro giro) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(giro);
        transaction.commit();
        System.out.println("Giro con id " + giro.getId() + " salvato nel DB");
    }

    public void saveList(List<Giro> giroList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Giro giro : giroList) {
            em.persist(giro);
        }
        transaction.commit();
        System.out.println("Giri aggiunti nel DB");
    }

    public Giro getByID(String id) {
        Giro elementFound = em.find(Giro.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Giro elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Elemento con id " + elementFound.getId() + " eliminato");
    }
}

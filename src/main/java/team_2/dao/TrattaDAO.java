package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tratta;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tratta);
        transaction.commit();
        System.out.println("Tratta con id " + tratta.getId() + " salvato nel DB");
    }

    public void saveList(List<Tratta> trattaList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Tratta tratta : trattaList) {
            em.persist(tratta);
        }
        transaction.commit();
        System.out.println("Tratte aggiunti nel DB");
    }

    public Tratta getByID(String id) {
        Tratta elementFound = em.find(Tratta.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Tratta elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Tratta con id " + elementFound.getId() + " eliminato");
    }
}

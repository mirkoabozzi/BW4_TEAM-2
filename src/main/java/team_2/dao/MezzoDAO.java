package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Mezzo;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class MezzoDAO {
    private final EntityManager em;

    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(mezzo);
        transaction.commit();
        System.out.println("Mezzo con id " + mezzo.getId() + " salvata nel DB");
    }

    public void saveList(List<Mezzo> mezzoList) {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Mezzo mezzo : mezzoList) {
            em.persist(mezzo);
        }
        transaction.commit();
        System.out.println("Mezzi aggiunti nel DB");
    }

    public Mezzo getById(String id) {
        Mezzo elementFound = em.find(Mezzo.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String mezzoId) {
        Mezzo elementFound = this.getById(mezzoId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(elementFound);
        transaction.commit();
        System.out.println("Il Mezzo con id " + elementFound.getId() + " è stato eliminata correttamente!");
    }

}

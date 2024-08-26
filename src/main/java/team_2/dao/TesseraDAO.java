package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tessera;
import team_2.exceptions.NotFoundException;

import java.util.UUID;

public class TesseraDAO {
    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tessera);
        transaction.commit();
        System.out.println("Tessera con id " + tessera.getId() + " aggiunto nel DB");
    }

    public Tessera getById(String id) {
        Tessera elementFound = em.find(Tessera.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void findByIdAndDelete(String tesseraId) {
        Tessera found = this.getById(tesseraId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("La tessera " + found.getId() + " è stato eliminata correttamente!");
    }
}

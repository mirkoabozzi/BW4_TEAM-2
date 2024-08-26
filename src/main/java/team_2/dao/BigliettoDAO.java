package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Biglietto;
import team_2.exceptions.NotFoundException;

import java.util.UUID;

public class BigliettoDAO {
    private final EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Biglietto biglietto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(biglietto);
        transaction.commit();
        System.out.println("Biglietto con id " + biglietto.getId() + " aggiunto nel DB");
    }

    public Biglietto getById(String id) {
        Biglietto elementFound = em.find(Biglietto.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }
}

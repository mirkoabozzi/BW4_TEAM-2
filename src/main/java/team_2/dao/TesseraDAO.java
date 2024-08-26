package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tessera;

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
}

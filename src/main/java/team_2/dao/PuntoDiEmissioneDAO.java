package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.PuntoDiEmissione;

public class PuntoDiEmissioneDAO {
    private final EntityManager em;

    public PuntoDiEmissioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(PuntoDiEmissione puntoDiEmissione) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(puntoDiEmissione);
        transaction.commit();
        System.out.println("Punto di emissione con id " + puntoDiEmissione.getId() + " aggiunto nel DB");
    }
}

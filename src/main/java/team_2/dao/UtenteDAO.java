package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Utente;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(utente);
        transaction.commit();
        System.out.println("Utente " + utente.getNome() + " aggiunto nel DB");
    }
}

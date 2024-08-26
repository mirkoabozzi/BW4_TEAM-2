package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Utente;
import team_2.exceptions.NotFoundException;

import java.util.UUID;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();

        em.persist(utente);

        System.out.println("Utente " + utente.getNome() + " aggiunto nel DB");
    }

    public Utente getById(String id) {
        Utente elementFound = em.find(Utente.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void findByIdAndDelete(String utenteId) {
        Utente found = this.getById(utenteId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("L'utente " + found.getNome() + " " + found.getCognome() + " è stato eliminato correttamente!");
    }
}

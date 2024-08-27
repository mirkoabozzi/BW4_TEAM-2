package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Utente;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

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
        System.out.println("Utente " + utente.getNome() + " con id " + utente.getId() + " salvato nel DB");
    }

    public void saveList(List<Utente> utenteList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Utente utente : utenteList) {
            em.persist(utente);
        }
        transaction.commit();
        System.out.println("Utenti aggiunti nel DB");
    }

    public Utente getById(String id) {
        Utente elementFound = em.find(Utente.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String utenteId) {
        Utente found = this.getById(utenteId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("L'utente " + found.getNome() + " " + found.getCognome() + " è stato eliminato correttamente!");
    }
}

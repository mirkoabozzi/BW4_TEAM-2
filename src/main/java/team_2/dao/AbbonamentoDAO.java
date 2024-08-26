package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Abbonamento;
import team_2.exceptions.NotFoundException;

import java.util.UUID;

public class AbbonamentoDAO {
    private final EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Abbonamento abbonamento) {
        EntityTransaction transaction = em.getTransaction();

        em.persist(abbonamento);

        System.out.println("Abbonamento con id " + abbonamento.getId() + " aggiunto nel DB");
    }

    public Abbonamento getByID(String id) {
        Abbonamento elementFound = em.find(Abbonamento.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }
}

package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Abbonamento;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class AbbonamentoDAO {
    private final EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(List<Abbonamento> abbonamentoList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Abbonamento abbonamento : abbonamentoList) {
            em.persist(abbonamento);
        }
        transaction.commit();
        System.out.println("Abbonamenti aggiunti nel DB");
    }

    public Abbonamento getByID(String id) {
        Abbonamento elementFound = em.find(Abbonamento.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Abbonamento elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Elemento con id " + elementFound.getId() + " eliminato");
    }
}

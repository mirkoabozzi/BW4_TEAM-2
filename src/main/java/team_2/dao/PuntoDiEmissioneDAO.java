package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.PuntoDiEmissione;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

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
        System.out.println("Punto di emissione con id " + puntoDiEmissione.getId() + " salvato nel DB");
    }

    public void saveList(List<PuntoDiEmissione> puntoDiEmissioneList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (PuntoDiEmissione puntoDiEmissione : puntoDiEmissioneList) {
            em.persist(puntoDiEmissione);
        }
        transaction.commit();
        System.out.println("Punti di emissione aggiunti nel DB");
    }

    public PuntoDiEmissione getById(String id) {
        PuntoDiEmissione elementFound = em.find(PuntoDiEmissione.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        PuntoDiEmissione elementFound = this.getById(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Elemento con id " + elementFound.getId() + " eliminato");
    }
}

package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import team_2.entities.PuntoDiEmissione;
import team_2.exceptions.NotFoundException;

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
    //Query

    //1.Calcola quanti punti di Emissione ci sono:

    public Long contaPuntiDiEmissione() {
        String jpql = "SELECT COUNT(p) FROM PuntoDiEmissione p";
        Query query = em.createQuery(jpql);
        Long count = (Long) query.getSingleResult();
        return count;
    }
    // 2.Calcola quanti RivenditoriAutorizzati ci sono:

    public Long contaRivenditoriAutorizzati() {
        String jpql = "SELECT COUNT(r) FROM RivenditoriAutorizzati r";
        Query query = em.createQuery(jpql);
        return (Long) query.getSingleResult();
    }

    // 2.1 Calcola quanti DistributoriAutomatici ci sono:

    public Long contaDistributoriAutomatici() {
        String jpql = "SELECT COUNT(d) FROM DistributoriAutomatici d";
        Query query = em.createQuery(jpql);
        return (Long) query.getSingleResult();


    }
}


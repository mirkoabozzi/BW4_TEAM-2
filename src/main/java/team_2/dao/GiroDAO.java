package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Giro;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class GiroDAO {
    private final EntityManager em;

    public GiroDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Giro giro) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(giro);
        transaction.commit();
        System.out.println("Giro con id " + giro.getId() + " salvato nel DB");
    }


    public Giro getByID(String id) {
        Giro elementFound = em.find(Giro.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Giro elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Elemento con id " + elementFound.getId() + " eliminato");
    }

    //QUERY

    //TROVIAMO LA TRATTA CHE FA IL DETERMINATO GIRO
    public List<Giro> trattaGiro(UUID trattaId) {
        TypedQuery<Giro> query = em.createQuery("SELECT g FROM Giro g WHERE g.tratta = :trattaId", Giro.class);
        query.setParameter("trattaId", trattaId);
        List<Giro> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun abbonamento trovato per la tessera " + trattaId);
        return risultatoQuery;
    }

    //TROVIAMO LE LISTE DI TESSERE (UTENTI) PRESENTI NEL GIRO
    public List<Giro> tessereGiro(String listaTessere) {
        TypedQuery<Giro> query = em.createQuery("SELECT g FROM Giro g WHERE g.tesseraList = :listaPerTessere", Giro.class);
        query.setParameter("listaPerTessere", listaTessere);
        return query.getResultList();
    }

    //TROVIAMO CHE MEZZO STA FACENDO IL GIRO
    public List<Giro> mezzoGiro(String mezzoGiro) {
        TypedQuery<Giro> query = em.createQuery("SELECT g FROM Giro g WHERE g.mezzo = :mezzoGiro", Giro.class);
        query.setParameter("mezzoGiro", mezzoGiro);
        return query.getResultList();
    }

}

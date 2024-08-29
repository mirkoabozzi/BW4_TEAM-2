package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Giro;
import team_2.entities.Mezzo;
import team_2.entities.Tessera;
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
    public Giro trovaTrattaPerGiro(UUID giroId) {
        TypedQuery<Giro> query = em.createQuery("SELECT g FROM Giro g WHERE g.id = :giroId", Giro.class);
        query.setParameter("giroId", giroId);
        return query.getSingleResult();
    }

    //TROVIAMO LE LISTE DI TESSERE (UTENTI) PRESENTI NEL GIRO
    public List<Tessera> trovaTesserePerGiro(UUID giroId) {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.giroId.id = :giroId", Tessera.class);
        query.setParameter("giroId", giroId);
        return query.getResultList();
    }

    //TROVIAMO CHE MEZZO STA FACENDO IL GIRO
    public Mezzo trovaMezzoPerGiro(UUID giroId) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT g.mezzo FROM Giro g WHERE g.id = :giroId", Mezzo.class);
        query.setParameter("giroId", giroId);
        return query.getSingleResult();
    }

}

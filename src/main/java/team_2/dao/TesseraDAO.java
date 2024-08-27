package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Tessera;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class TesseraDAO {
    private final EntityManager em;

    public TesseraDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Tessera tessera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tessera);
        transaction.commit();
        System.out.println("Tessera con id " + tessera.getId() + " salvata nel DB");
    }

    public void saveList(List<Tessera> tesseraList) {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Tessera tessera : tesseraList) {
            em.persist(tessera);
            tessera.getUtente().setTessera(tessera);
            em.persist(tessera.getUtente());
        }
        transaction.commit();
        System.out.println("Tessere aggiunte nel DB");
    }

    public Tessera getById(String id) {
        Tessera elementFound = em.find(Tessera.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String tesseraId) {
        Tessera found = this.getById(tesseraId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("La tessera " + found.getId() + " è stato eliminata correttamente!");
    }

    public Tessera getByUtente(UUID utenteId) {
        TypedQuery<Tessera> query = em.createQuery("SELECT t FROM Tessera t WHERE t.utente.id = :utenteId", Tessera.class);
        query.setParameter("utenteId", utenteId);
        List<Tessera> result = query.getResultList();
        return result.isEmpty() ? null : result.getFirst();
    }
}

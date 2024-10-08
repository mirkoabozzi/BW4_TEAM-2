package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team_2.entities.Tessera;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
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
        tessera.getUtente().setTessera(tessera);
        em.persist(tessera.getUtente());
        transaction.commit();
        System.out.println("Tessera con id " + tessera.getId() + " salvata nel DB");
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
    // Query

    // 1. Contare numero tessere valide

    public Long contaTessereValide() {
        String jpql = "SELECT COUNT(t) FROM Tessera t WHERE t.validitàTessera = true";
        Query query = em.createQuery(jpql);
        return (Long) query.getSingleResult();
    }

    // 2. Ottenere tessere per un determinato punto di emissione

    public List<Tessera> trovaTesserePerPuntoDiEmissione(UUID puntoDiEmissioneId) {
        String jpql = "SELECT t FROM Tessera t WHERE t.puntoDiEmissione.id = :puntoDiEmissioneId";
        TypedQuery<Tessera> query = em.createQuery(jpql, Tessera.class);
        query.setParameter("puntoDiEmissioneId", puntoDiEmissioneId);
        return query.getResultList();
    }

    // 3. Trovare tutte le tessere scadute

    public List<Tessera> trovaTessereScadute(LocalDate dataCorrente) {
        String jpql = "SELECT t FROM Tessera t WHERE t.dataFine < :dataCorrente";
        TypedQuery<Tessera> query = em.createQuery(jpql, Tessera.class);
        query.setParameter("dataCorrente", LocalDate.now());
        return query.getResultList();
    }

    //4. Trovare tessere con una data di inizio specifica
    public List<Tessera> trovaTesserePerDataInizio(LocalDate dataInizio) {
        String jpql = "SELECT t FROM Tessera t WHERE t.dataInizio = :dataInizio";
        TypedQuery<Tessera> query = em.createQuery(jpql, Tessera.class);
        query.setParameter("dataInizio", dataInizio);
        return query.getResultList();
    }

    //5. Inserisci l'id tessera e visualizzi se l'abbonamento è attivo
    public List<Tessera> validitàTessera(UUID idTessera) {
        String jpql = "SELECT t FROM Tessera t WHERE t.id = :idTessera";
        TypedQuery<Tessera> query = em.createQuery(jpql, Tessera.class);
        query.setParameter("idTessera", idTessera);
        List<Tessera> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun tessera trovata con l'id inserito " + idTessera);
        return risultatoQuery;
    }

    //6. Quanti biglietti sono associati a una tessera.

    public long contaBigliettiAssociati(UUID idTessera) {
        String jpql = "SELECT COUNT(b) FROM Biglietto b WHERE b.tessera.id = :idTessera"; // Adjust according to your entity mapping
        TypedQuery<Long> query = em.createQuery(jpql, Long.class);
        query.setParameter("idTessera", idTessera);
        long count = query.getSingleResult();
        System.out.println("Numero di biglietti associati alla tessera con id " + idTessera + ": " + count);
        return count;
    }
}
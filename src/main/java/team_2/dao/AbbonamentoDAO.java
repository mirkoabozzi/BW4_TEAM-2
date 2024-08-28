package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team_2.entities.Abbonamento;
import team_2.enums.StatoAbbonamento;
import team_2.enums.TipoAbbonamento;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AbbonamentoDAO {
    private final EntityManager em;

    public AbbonamentoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Abbonamento abbonamento) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(abbonamento);
        transaction.commit();
        System.out.println("Abbonamento con id " + abbonamento.getId() + " salvato nel DB");
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

    public List<Abbonamento> filtraAbbonamentiPerStato(StatoAbbonamento statoAbbonamento) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.statoAbbonamento = :stato", Abbonamento.class);
        query.setParameter("stato", statoAbbonamento);
        return query.getResultList();
    }

    public List<Abbonamento> filtraAbbonamentiRinnovatiInData(String data) {
        LocalDate d = LocalDate.parse(data);
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.dataUltimoRinnovo = :data", Abbonamento.class);
        query.setParameter("data", d);
        List<Abbonamento> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun abbonamento rinnovato in data " + data);
        return risultatoQuery;
    }

    public List<Abbonamento> filtraAbbonamentiPerTipo(TipoAbbonamento tipoAbbonamento) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a where a.tipoAbbonamento = :tipo", Abbonamento.class);
        query.setParameter("tipo", tipoAbbonamento);
        List<Abbonamento> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun abbonamento trovato di tipo " + tipoAbbonamento);
        return risultatoQuery;
    }

    public List<Abbonamento> trovaAbbonamentiTramiteTessera(UUID tesseraId) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.tessera.id = :tesseraId", Abbonamento.class);
        query.setParameter("tesseraId", tesseraId);
        List<Abbonamento> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun abbonamento trovato per la tessera " + tesseraId);
        return risultatoQuery;
    }

    public Long contaAbbonamentiPerTipo(TipoAbbonamento tipoAbbonamento) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.tipoAbbonamento = :tipo", Long.class);
        query.setParameter("tipo", tipoAbbonamento);
        return query.getSingleResult();
    }

    public Long contaAbbonamentiPerStato(StatoAbbonamento statoAbbonamento) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(a) FROM Abbonamento a WHERE a.statoAbbonamento = :stato", Long.class);
        query.setParameter("stato", statoAbbonamento);
        return query.getSingleResult();
    }

    public List<Abbonamento> abbonamentiAttiviInScadenzaEntroGiorni(int giorni) {
        LocalDate data = LocalDate.now().plusDays(giorni);
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento a WHERE a.statoAbbonamento = ATTIVO AND a.dataUltimoRinnovo < :data", Abbonamento.class);
        query.setParameter("data", data);
        return query.getResultList();
    }

    public void rinnovaAbbonamentoEAttiva(UUID id, LocalDate newData) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query update = em.createQuery("UPDATE Abbonamento a SET a.dataUltimoRinnovo = :newData, a.statoAbbonamento = ATTIVO WHERE a.id = :id");
        update.setParameter("newData", newData);
        update.setParameter("id", id);
        int modificati = update.executeUpdate();
        if (modificati > 0) {
            transaction.commit();
            System.out.println("Abbonamento con id " + id + " rinnovato e attivato con successo");
        } else {
            transaction.rollback();
            System.out.println("Nessun abbonamento trovato con id " + id);
        }
    }
}

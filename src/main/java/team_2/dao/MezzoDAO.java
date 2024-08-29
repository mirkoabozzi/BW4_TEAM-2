package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Manutenzione;
import team_2.entities.Mezzo;
import team_2.enums.TipoMezzo;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class MezzoDAO {
    private final EntityManager em;

    public MezzoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Mezzo mezzo) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(mezzo);
        transaction.commit();
        System.out.println("Mezzo con id " + mezzo.getId() + " salvata nel DB");
    }

    public void saveList(List<Mezzo> mezzoList) {

        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Mezzo mezzo : mezzoList) {
            em.persist(mezzo);
        }
        transaction.commit();
        System.out.println("Mezzi aggiunti nel DB");
    }

    public Mezzo getById(String id) {
        Mezzo elementFound = em.find(Mezzo.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String mezzoId) {
        Mezzo elementFound = this.getById(mezzoId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(elementFound);
        transaction.commit();
        System.out.println("Il Mezzo con id " + elementFound.getId() + " è stato eliminata correttamente!");
    }

    //QUERY

    //TROVIAMO QUANTE PERSONE CI STANNO NEL MEZZO
    public int trovaCapienzaMezzo(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.getCapienza();
    }

    //TROVIAMO SE E' IN SERVIZIO IL MEZZO
    public boolean trovaStatoInServizioMezzo(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.isInServizio();
    }

    //TROVIAMO LA DATA D'INIZIO DEL SERVIZIO DEL MEZZO
    public LocalDate trovaDataInizioServizio(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.getDataInizioServizio();
    }

    //TROVIAMO LA DATA DI FINE  DEL SERVIZIO DEL MEZZO
    public LocalDate trovaDataFineServizio(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.getDataFineServizio();
    }

    //TROVIAMO IL NUMERO DEL MEZZO
    public int trovaNumeroMezzo(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.getNumeroMezzo();
    }

    //TROVIAMO IL TIPO DI MEZZO
    public TipoMezzo trovaTipoMezzo(UUID mezzoId) {
        Mezzo mezzo = this.getById(mezzoId.toString());
        return mezzo.getTipoMezzo();
    }

    //TROVIAMO TUTTE LE MANUNTENZIONE RICEVUTE
    public List<Manutenzione> trovaManutenzioniPerMezzo(UUID mezzoId) {
        TypedQuery<Manutenzione> query = em.createQuery("SELECT m FROM Manutenzione m WHERE m.mezzo.id = :mezzoId", Manutenzione.class);
        query.setParameter("mezzoId", mezzoId);
        return query.getResultList();
    }

    //TROVIAMO IL NUMERO DI GIRI IN UNA DETERMINATA TRATTA
    public Long trovaNumeroDiGiriInTratta(UUID trattaId) {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(g) FROM Giro g WHERE g.tratta.id = :trattaId", Long.class);
        query.setParameter("trattaId", trattaId);
        return query.getSingleResult();
    }
}

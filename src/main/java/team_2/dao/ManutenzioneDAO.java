package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import team_2.entities.Manutenzione;
import team_2.entities.Mezzo;
import team_2.enums.TipoMezzo;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ManutenzioneDAO {
    private final EntityManager em;

    public ManutenzioneDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Manutenzione manutenzione) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(manutenzione);
        transaction.commit();
        System.out.println("Manutenzione con id " + manutenzione.getId() + " salvato nel DB");
    }

    public Manutenzione getByID(String id) {
        Manutenzione elementFound = em.find(Manutenzione.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Manutenzione elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Manutenzione con id " + elementFound.getId() + " eliminata");
    }

    public List<Mezzo> filtraMezziPerTipo(TipoMezzo tipoMezzo) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE m.tipoMezzo = :tipo", Mezzo.class);
        query.setParameter("tipo", tipoMezzo);
        List<Mezzo> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun mezzo trovato di tipo " + tipoMezzo);
        return risultatoQuery;
    }

    public List<Mezzo> mezziInManutenzione() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Mezzo> query = em.createQuery("SELECT m.mezzo FROM Manutenzione m WHERE :oggi BETWEEN m.dataInizio AND m.dataFine", Mezzo.class);
        query.setParameter("oggi", oggi);
        List<Mezzo> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun mezzo presente in officina");
        return risultatoQuery;
    }

    public long contaMezziInManutenzionePerTipo(TipoMezzo tipoMezzo) {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(DISTINCT m.mezzo) FROM Manutenzione m WHERE :oggi BETWEEN m.dataInizio AND m.dataFine AND m.mezzo.tipoMezzo = :tipo", Long.class);
        query.setParameter("oggi", oggi);
        query.setParameter("tipo", tipoMezzo);
        return query.getSingleResult();
    }

    public void estendiFineManutenzione(UUID id, LocalDate newData) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query update = em.createQuery("UPDATE Manutenzione m SET m.dataFine = :data WHERE m.id = :id");
        update.setParameter("data", newData);
        update.setParameter("id", id);
        int modificati = update.executeUpdate();
        if (modificati > 0) {
            transaction.commit();
            System.out.println("Data fine manutenzione per il mezzo con id " + id + " aggiornata con successo");
        } else {
            transaction.rollback();
            System.out.println("Nessun mezzo trovato con id " + id);
        }

    }
}

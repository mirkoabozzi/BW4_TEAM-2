package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Mezzo;
import team_2.exceptions.NotFoundException;

import java.util.Date;
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
    public List<Mezzo> capienzaMezzo(int totCapienza) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.capienza = :totCapienza", Mezzo.class);
        query.setParameter("totCapienza", totCapienza);
        return query.getResultList();
    }

    //TROVIAMO LE TRATTE  CHE FA IL DETERMINATO MEZZO
    public List<Mezzo> TrattaMezzo(int trattaLista) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.trattaList = :trattaLista", Mezzo.class);
        query.setParameter("trattaLista", trattaLista);
        return query.getResultList();
    }

    //TROVIAMO SE E' IN SERVIZIO IL MEZZO
    public List<Mezzo> operativitàMezzo(boolean inServizio) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.inServizio = :inServizio", Mezzo.class);
        query.setParameter("inServizio", inServizio);
        return query.getResultList();
    }

    //TROVIAMO LA DATA D'INIZIO DEL SERVIZIO DEL MEZZO
    public List<Mezzo> inizioMezzo(Date dataInizioServizio) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.dataInizioServizio = :dataInizioServizio", Mezzo.class);
        query.setParameter("dataInizioServizio", dataInizioServizio);
        return query.getResultList();
    }

    //TROVIAMO LA DATA DI FINE  DEL SERVIZIO DEL MEZZO
    public List<Mezzo> fineMezzo(Date dataFineServizio) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.dataFineServizio = :dataFineServizio", Mezzo.class);
        query.setParameter("dataFineServizio", dataFineServizio);
        return query.getResultList();
    }

    //TROVIAMO IL NUMERO DEL MEZZO
    public List<Mezzo> numeroMezzo(String numeroMezzo) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.numeroMezzo = :numeroMezzo", Mezzo.class);
        query.setParameter("numeroMezzo", numeroMezzo);
        return query.getResultList();
    }

    //TROVIAMO IL TIPO DI MEZZO
    public List<Mezzo> tipoMezzo(String tipoMezzo) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.tipoMezzo = :tipoMezzo", Mezzo.class);
        query.setParameter("tipoMezzo", tipoMezzo);
        return query.getResultList();
    }

    //TROVIAMO TUTTE LE MANUNTENZIONE RICEVUTE
    public List<Mezzo> manuntenzioneMezzo(String tipomanuntenzioniMezzo) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.manutenzioneList = :tipomanuntenzioniMezzo", Mezzo.class);
        query.setParameter("tipomanuntenzioniMezzo", tipomanuntenzioniMezzo);
        return query.getResultList();
    }

    //TROVIAMO IL NUMERO DI GIRI IN UNA DETERMINATA TRATTA
    public List<Mezzo> giriMezzo(String numeroGiriMezzo) {
        TypedQuery<Mezzo> query = em.createQuery("SELECT m FROM Mezzo m WHERE g.giroList = :numeroGiriMezzo", Mezzo.class);
        query.setParameter("numeroGiriMezzo", numeroGiriMezzo);
        return query.getResultList();
    }
}

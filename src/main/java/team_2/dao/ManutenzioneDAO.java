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
        return query.getResultList();
    }

    public List<Mezzo> mezziInManutenzione() {
        LocalDate oggi = LocalDate.now();
        TypedQuery<Mezzo> query = em.createQuery("SELECT m.mezzo FROM Manutenzione m WHERE :oggi BETWEEN m.dataInizio AND m.dataFine", Mezzo.class);
        query.setParameter("oggi", oggi);
        return query.getResultList();
    }
}

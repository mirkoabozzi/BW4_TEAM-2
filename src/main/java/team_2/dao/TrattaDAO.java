package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tratta;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class TrattaDAO {
    private final EntityManager em;

    public TrattaDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Tratta tratta) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(tratta);
        transaction.commit();
        System.out.println("Tratta con id " + tratta.getId() + " salvato nel DB");
    }


    public Tratta getByID(String id) {
        Tratta elementFound = em.find(Tratta.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }


    public void deleteById(String id) {
        Tratta elementFound = this.getByID(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Tratta con id " + elementFound.getId() + " eliminato");
    }

    //ottenere tutte le tratte
    public List<Tratta> getAllTratte() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    //ottenere tratte con una zona di partenza specifica
    public List<Tratta> getTratteByZonaPartenza(String zonaPartenza) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.zanaPartenza = :zonaPartenza", Tratta.class)
                .setParameter("zonaPartenza", zonaPartenza)
                .getResultList();
    }

    //  ottenere tutte le tratte che partono in un orario specifico
    public List<Tratta> getTratteByOrarioPartenza(double orario) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.orarioPartenza = :orario", Tratta.class)
                .setParameter("orario", orario)
                .getResultList();
    }

    //ottenere tratte associate a un determinato mezzo di trasporto
    public List<Tratta> getTratteByMezzoId(UUID mezzoId) {
        return em.createQuery("SELECT t FROM Tratta t JOIN t.mezzoList m WHERE m.id = :mezzoId", Tratta.class)
                .setParameter("mezzoId", mezzoId)
                .getResultList();
    }

    // ottenere tratte tra una zona di partenza e un capolinea specifico
    public List<Tratta> getTratteByZonaPartenzaAndCapolinea(String zonaPartenza, String capolinea) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.zanaPartenza = :zonaPartenza AND t.capolinea = :capolinea", Tratta.class)
                .setParameter("zonaPartenza", zonaPartenza)
                .setParameter("capolinea", capolinea)
                .getResultList();
    }

    //  ottenere tutte le tratte ordinate per tempo di percorrenza
    public List<Tratta> getTratteOrderByTempoPercorrenza() {
        return em.createQuery("SELECT t FROM Tratta t ORDER BY t.tempoPercorrenza", Tratta.class)
                .getResultList();
    }

    // ottenere la tratta con il tempo di percorrenza più veloce
    public Tratta getTrattaConTempoPercorrenzaPiuVeloce() {
        return em.createQuery("SELECT t FROM Tratta t ORDER BY t.tempoPercorrenza ASC", Tratta.class)
                .setMaxResults(1)  // Limitiamo il risultato a una singola tratta
                .getSingleResult();
    }
}

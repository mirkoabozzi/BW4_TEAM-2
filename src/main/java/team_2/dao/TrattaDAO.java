package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tratta;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
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
        if (elementFound == null) {
            throw new NotFoundException(id);
        } else {
            return elementFound;
        }
    }

    public void deleteById(String id) {
        Tratta elementFound = this.getByID(id);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(elementFound);
        transaction.commit();
        System.out.println("Tratta con id " + elementFound.getId() + " eliminato");
    }

    // Ottenere tutte le tratte
    public List<Tratta> getAllTratte() {
        return em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
    }

    // Ottenere tratte con una zona di partenza specifica
    public List<Tratta> getTratteByZonaPartenza(String zanaPartenza) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.zanaPartenza = :zanaPartenza", Tratta.class)
                .setParameter("zanaPartenza", zanaPartenza)
                .getResultList();
    }

    // Ottenere tutte le tratte che partono in un orario specifico
    public List<Tratta> getTratteByOrarioPartenza(double orario) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.orarioPartenza = :orario", Tratta.class)
                .setParameter("orario", orario)
                .getResultList();
    }

    // Ottenere tratte associate a un determinato mezzo di trasporto
    public List<Tratta> getTratteByMezzoId(UUID mezzoId) {
        return em.createQuery("SELECT t FROM Tratta t JOIN t.mezzoList m WHERE m.id = :mezzoId", Tratta.class)
                .setParameter("mezzoId", mezzoId)
                .getResultList();
    }

    // Ottenere tratte tra una zona di partenza e un capolinea specifico
    public List<Tratta> getTratteByZonaPartenzaAndCapolinea(String zanaPartenza, String capolinea) {
        return em.createQuery("SELECT t FROM Tratta t WHERE t.zanaPartenza = :zanaPartenza AND t.capolinea = :capolinea", Tratta.class)
                .setParameter("zanaPartenza", zanaPartenza)
                .setParameter("capolinea", capolinea)
                .getResultList();
    }

    // Ottenere tutte le tratte ordinate per tempo di percorrenza previsto
    public List<Tratta> getTratteOrderByTempoPercorrenza() {
        return em.createQuery("SELECT t FROM Tratta t ORDER BY t.tempoPercorrenzaPrevisto", Tratta.class)
                .getResultList();
    }

    // Ottenere la tratta con il tempo di percorrenza previsto più veloce
    public Tratta getTrattaConTempoPercorrenzaPiuVeloce() {
        return em.createQuery("SELECT t FROM Tratta t ORDER BY t.tempoPercorrenzaPrevisto ASC", Tratta.class)
                .setMaxResults(1)  // Limitiamo il risultato a una singola tratta
                .getSingleResult();
    }

    // Ottenere la tratta con il tempo di percorrenza previsto più veloce in base a una data
    public Tratta findTrattaPiuVeloceFromData(LocalDate data) {
        return em.createQuery("SELECT t FROM Tratta t " + "JOIN t.mezzoList m " + "WHERE m.inServizio = true " +
                        "AND :data BETWEEN m.dataInizioServizio AND COALESCE(m.dataFineServizio, :data) " +
                        "ORDER BY t.tempoPercorrenzaPrevisto ASC", Tratta.class).setParameter("data", data)
                .setMaxResults(1).getSingleResult();
    }

    //Ottenere la tratta con il numero più alto di biglietti vidimati
    public Tratta findTrattaConIlNumeroPiuAltoDiBigliettiVidimati() {
        return em.createQuery("SELECT t FROM Tratta t " +
                        "JOIN t.giroList g "
                        + "JOIN g.tesseraList te " +
                        "JOIN te.listaBiglietto lb WHERE lb.vidimato=true  "
                        + "GROUP BY t " + "ORDER BY COUNT(lb) DESC", Tratta.class)
                .setMaxResults(1).getSingleResult();
    }

    //Ottenere la tratta con il numero più alto di abbonamenti
    public Tratta findTrattaConIlNumeroPiuAltoDiAbbonamenti() {
        return em.createQuery("SELECT t FROM Tratta t " +
                        "JOIN t.giroList g "
                        + "JOIN g.tesseraList te " +
                        "JOIN te.listaAbbonamento la   "
                        + "GROUP BY t " + "ORDER BY COUNT(la) DESC", Tratta.class)
                .setMaxResults(1).getSingleResult();
    }
}

package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Biglietto;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class BigliettoDAO {
    private final EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(Biglietto biglietto) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(biglietto);
        transaction.commit();
        System.out.println("Biglietto con id " + biglietto.getId() + " salvato nel DB");
    }

    public Biglietto getById(String id) {
        Biglietto elementFound = em.find(Biglietto.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }

    public void deleteById(String id) {
        Biglietto elementFound = this.getById(id);
        EntityTransaction transition = em.getTransaction();
        transition.begin();
        em.remove(elementFound);
        transition.commit();
        System.out.println("Elemento con id " + elementFound.getId() + " eliminato");
    }

    public List<Biglietto> filtraAbbonamentiPerStato(Boolean statoBiglietto) {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.vidimato = :stato", Biglietto.class);
        query.setParameter("stato", statoBiglietto);
        return query.getResultList();
    }

    public List<Biglietto> filtraBigliettiVidimatiInData(String data) {
        LocalDate d = LocalDate.parse(data);
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.dataVidimazione = :data", Biglietto.class);
        query.setParameter("data", d);
        List<Biglietto> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun biglietto vidimato in data " + data);
        return risultatoQuery;
    }

    public List<Biglietto> trovaBigliettiTramiteTessera(UUID tesseraId) {
        TypedQuery<Biglietto> query = em.createQuery("SELECT b FROM Biglietto b WHERE b.tessera.id = :tesseraId", Biglietto.class);
        query.setParameter("tesseraId", tesseraId);
        return query.getResultList();
    }

    public long contaBigliettiVidimati() {
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(b) FROM Biglietto b WHERE b.vidimato = true", Long.class);
        return query.getSingleResult();
    }
}

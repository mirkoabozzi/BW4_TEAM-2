package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import team_2.entities.Abbonamento;
import team_2.enums.StatoAbbonamento;
import team_2.enums.Tipo;
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
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento WHERE a.dataUltimoRinnovo = :data", Abbonamento.class);
        query.setParameter("data", d);
        List<Abbonamento> risultatoQuery = query.getResultList();
        if (risultatoQuery.isEmpty())
            System.out.println("Nessun abbonamento rinnovato in data " + data);
        return risultatoQuery;
    }

    public List<Abbonamento> filtraAbbonamentiPerTipo(Tipo tipoAbbonamento) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT a FROM Abbonamento where a.tipo = :tipo", Abbonamento.class);
        query.setParameter("tipo", tipoAbbonamento);
        return query.getResultList();
    }

    public List<Abbonamento> trovaAbbonamentiTramiteTessera(UUID tesseraId) {
        TypedQuery<Abbonamento> query = em.createQuery("SELECT b FROM Abbonamento b WHERE b.tessera.id = :tesseraId", Abbonamento.class);
        query.setParameter("tesseraId", tesseraId);
        return query.getResultList();
    }

}

package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Biglietto;
import team_2.exceptions.NotFoundException;

import java.util.List;
import java.util.UUID;

public class BigliettoDAO {
    private final EntityManager em;

    public BigliettoDAO(EntityManager em) {
        this.em = em;
    }

    public void save(List<Biglietto> bigliettoList) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        for (Biglietto biglietto : bigliettoList) {
            em.persist(biglietto);
        }
        transaction.commit();
        System.out.println("Biglietti aggiunti nel DB");
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
}

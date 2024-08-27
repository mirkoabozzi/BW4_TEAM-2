package team_2.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import team_2.entities.Tessera;
import team_2.entities.Utente;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class UtenteDAO {
    private final EntityManager em;

    public UtenteDAO(EntityManager em) {
        this.em = em;
    }


    public void save(Utente utente) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.persist(utente);
        transaction.commit();
        System.out.println("Utente " + utente.getNome() + " con id " + utente.getId() + " salvato nel DB");
    }


    public Utente getById(String id) {
        Utente elementFound = em.find(Utente.class, UUID.fromString(id));
        if (elementFound == null)
            throw new NotFoundException(id);
        else return elementFound;
    }


    public void deleteById(String utenteId) {
        Utente found = this.getById(utenteId);
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        em.remove(found);
        transaction.commit();
        System.out.println("L'utente " + found.getNome() + " " + found.getCognome() + " è stato eliminato correttamente!");
    }

    // ottenere tutti gli utenti
    public List<Utente> getAllUtenti() {
        return em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
    }

    //  ottenere utenti per nome
    public List<Utente> getUtentiByNome(String nome) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.nome = :nome", Utente.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    //  ottenere utenti per cognome
    public List<Utente> getUtentiByCognome(String cognome) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.cognome = :cognome", Utente.class)
                .setParameter("cognome", cognome)
                .getResultList();
    }

    // ottenere utenti con età maggiore di x
    public List<Utente> getUtentiByEtaMaggioreDi(int eta) {
        return em.createQuery("SELECT u FROM Utente u WHERE FUNCTION('YEAR', CURRENT_DATE) - FUNCTION('YEAR', u.dataDiNascita) > :eta", Utente.class)
                .setParameter("eta", eta)
                .getResultList();
    }

    //  ottenere utenti con un numero tessera specifico
    public List<Utente> getUtentiByNumeroTessera(int numeroTessera) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.numeroTessera = :numeroTessera", Utente.class)
                .setParameter("numeroTessera", numeroTessera)
                .getResultList();
    }

    //ottenere utenti con una tessera specifica (byid)
    public List<Utente> getUtentiByTesseraId(UUID tesseraId) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.tessera.id = :tesseraId", Utente.class)
                .setParameter("tesseraId", tesseraId)
                .getResultList();
    }

    // ottenere utenti ordinati per nome
    public List<Utente> getUtentiOrderByNome() {
        return em.createQuery("SELECT u FROM Utente u ORDER BY u.nome ASC", Utente.class).getResultList();
    }

    // ottenere utenti ordinati per data di nascita
    public List<Utente> getUtentiOrderByDataDiNascita() {
        return em.createQuery("SELECT u FROM Utente u ORDER BY u.dataDiNascita ASC", Utente.class).getResultList();
    }

    // ottenere utenti per nome e cognome
    public List<Utente> getUtentiByNomeECognome(String nome, String cognome) {
        return em.createQuery("SELECT u FROM Utente u WHERE u.nome = :nome AND u.cognome = :cognome", Utente.class)
                .setParameter("nome", nome)
                .setParameter("cognome", cognome)
                .getResultList();
    }

    //  verificare se un utente ha un abbonamento attivo
    public boolean hasAbbonamentoAttivo(String utenteId) {

        Utente utente = getById(utenteId);

        if (utente.getTessera() == null) {
            return false;
        }

        Tessera tessera = utente.getTessera();


        if (tessera.isValiditàTessera() &&
                tessera.getDataInizio().isBefore(LocalDate.now()) &&
                tessera.getDataFine().isAfter(LocalDate.now())) {
            return true;
        }

        return false;
    }
}
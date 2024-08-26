package team_2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.dao.PuntoDiEmissioneDAO;
import team_2.dao.TesseraDAO;
import team_2.dao.UtenteDAO;
import team_2.entities.RivenditoriAutorizzati;
import team_2.entities.Tessera;
import team_2.entities.Utente;

import java.time.LocalDate;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
        EntityManager em = emf.createEntityManager();
        TesseraDAO td = new TesseraDAO(em);
        PuntoDiEmissioneDAO ped = new PuntoDiEmissioneDAO(em);
        UtenteDAO ud = new UtenteDAO(em);

        Utente utente1 = new Utente("Simone", "Rossi", LocalDate.of(2004, 10, 10), 40404);
        ud.save(utente1);
        RivenditoriAutorizzati bar = new RivenditoriAutorizzati("Bar di Gianni");
        ped.save(bar);

        Tessera tessera1 = new Tessera(LocalDate.of(2020, 5, 9), LocalDate.of(2020, 5, 10), true);
        tessera1.setUtente(utente1);
        tessera1.setPuntoDiEmissione(bar);
        td.save(tessera1);

    }
}

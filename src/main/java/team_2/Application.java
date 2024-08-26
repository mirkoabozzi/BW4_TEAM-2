package team_2;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import team_2.dao.*;
import team_2.entities.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.StatoDistributori;
import team_2.enums.Tipo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
    private static final Faker fk = new Faker();
    private static final Random random = new Random();

    public static Supplier<RivenditoriAutorizzati> rivenditoriAutorizzatiCreateOne = () ->
            new RivenditoriAutorizzati(fk.company().name());

    public static Supplier<DistributoriAutomatici> distributoriAutomaticiCreateOne = () -> {
        StatoDistributori[] statoDistributoriList = StatoDistributori.values();
        return new DistributoriAutomatici(statoDistributoriList[random.nextInt(statoDistributoriList.length)]);
    };

    public static Supplier<Utente> utenteCreateOne = () -> {
        LocalDate date = fk.date().birthday(12, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Utente(fk.name().firstName(), fk.name().lastName(), date, random.nextInt(111111111, 999999999));
    };

    public static Supplier<Tessera> tesseraCreateOne = () -> {
        LocalDate initialDate = LocalDate.now();
        LocalDate endDate = initialDate.plusMonths(random.nextInt(1, 24));
        return new Tessera(initialDate, endDate, random.nextBoolean());
    };

    public static Supplier<Abbonamento> abbonamentoCreateOne = () -> {
        LocalDate date = LocalDate.now();
        StatoAbbonamento[] statoAbbonamentoList = StatoAbbonamento.values();
        Tipo[] tipoList = Tipo.values();
        return new Abbonamento(date, statoAbbonamentoList[random.nextInt(statoAbbonamentoList.length)],
                tipoList[random.nextInt(tipoList.length)]);
    };

    public static Supplier<Biglietto> bigliettoCreateOne = () -> {
        LocalDate date = LocalDate.now();
        return new Biglietto(random.nextBoolean(), date);
    };

    private static <T> void createAndPersistEntities(Supplier<T> entitySupplier, String entityName, Scanner scanner, EntityManager em, Runnable daoAction) {
        try {
            int numOfEntities;
            while (true) {
                System.out.printf("Quanti %s vuoi creare?%n", entityName);
                try {
                    numOfEntities = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Inserisci un numero valido.");
                }
            }

            EntityTransaction transaction = em.getTransaction();
            transaction.begin();
            try {
                for (int i = 0; i < numOfEntities; i++) {
                    T entity = entitySupplier.get();
                    daoAction.run(); // Esegui l'azione del DAO qui
                }
                transaction.commit();
                System.out.printf("%s creati e salvati nel database con successo.%n", entityName);
            } catch (Exception e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                System.out.printf("Errore durante il salvataggio: %s%n", e.getMessage());
            }
        } catch (Exception e) {
            System.out.printf("Errore durante la creazione di %s: %s%n", entityName, e.getMessage());
        }
    }

    public static void createDistributoriAutomatici(Scanner scanner, PuntoDiEmissioneDAO dao, EntityManager em) {
        createAndPersistEntities(distributoriAutomaticiCreateOne, "distributori automatici", scanner, em, () -> dao.save(distributoriAutomaticiCreateOne.get()));
    }

    public static void createRivenditoriAutorizzati(Scanner scanner, PuntoDiEmissioneDAO dao, EntityManager em) {
        createAndPersistEntities(rivenditoriAutorizzatiCreateOne, "rivenditori autorizzati", scanner, em, () -> dao.save(rivenditoriAutorizzatiCreateOne.get()));
    }

    public static void createUtente(Scanner scanner, UtenteDAO utenteDAO, EntityManager em) {
        createAndPersistEntities(utenteCreateOne, "persone", scanner, em, () -> utenteDAO.save(utenteCreateOne.get()));
    }

    public static void createTessera(Scanner scanner, TesseraDAO tesseraDAO, EntityManager em) {
        createAndPersistEntities(tesseraCreateOne, "tessere", scanner, em, () -> tesseraDAO.save(tesseraCreateOne.get()));
    }

    public static void createAbbonamento(Scanner scanner, AbbonamentoDAO abbonamentoDAO, EntityManager em) {
        createAndPersistEntities(abbonamentoCreateOne, "abbonamenti", scanner, em, () -> abbonamentoDAO.save(abbonamentoCreateOne.get()));
    }

    public static void createBiglietto(Scanner scanner, BigliettoDAO bigliettoDAO, EntityManager em) {
        createAndPersistEntities(bigliettoCreateOne, "biglietti", scanner, em, () -> bigliettoDAO.save(bigliettoCreateOne.get()));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = emf.createEntityManager();


        UtenteDAO utenteDAO = new UtenteDAO(em);
        TesseraDAO tesseraDAO = new TesseraDAO(em);
        AbbonamentoDAO abbonamentoDAO = new AbbonamentoDAO(em);
        BigliettoDAO bigliettoDAO = new BigliettoDAO(em);
        PuntoDiEmissioneDAO puntoDiEmissioneDAO = new PuntoDiEmissioneDAO(em);

        System.out.println("Hello World!");


        createDistributoriAutomatici(scanner, puntoDiEmissioneDAO, em);
        createRivenditoriAutorizzati(scanner, puntoDiEmissioneDAO, em);
        createUtente(scanner, utenteDAO, em);
        createTessera(scanner, tesseraDAO, em);
        createAbbonamento(scanner, abbonamentoDAO, em);
        createBiglietto(scanner, bigliettoDAO, em);


        em.close();
        scanner.close();
    }
}
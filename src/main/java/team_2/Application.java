package team_2;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.entities.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.StatoDistributori;
import team_2.enums.Tipo;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.function.Supplier;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
    static Faker fk = new Faker();
    //createOne
    public static Supplier<RivenditoriAutorizzati> rivenditoriAutorizzatiCreateOne = () -> new RivenditoriAutorizzati(fk.company().name());
    static Random r = new Random();
    public static Supplier<DistributoriAutomatici> distributoriAutomaticiCreateOne = () -> {
        StatoDistributori[] statoDistributoriList = StatoDistributori.values();
        return new DistributoriAutomatici(statoDistributoriList[r.nextInt(statoDistributoriList.length)]);
    };
    public static Supplier<Utente> utenteCreateOne = () -> {
        LocalDate date = fk.date().birthday(12, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Utente(fk.name().firstName(), fk.name().lastName(), date, r.nextInt(111111111, 999999999));
    };
    public static Supplier<Tessera> tesseraCreateOne = () -> {
        LocalDate initialDate = fk.date().birthday(2, 5).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = fk.date().birthday(0, 2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Tessera(initialDate, endDate, r.nextInt(0, 1) == 0);
    };
    public static Supplier<Abbonamento> abbonamentoCreateOne = () -> {
        LocalDate date = fk.date().birthday(0, 10).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        StatoAbbonamento[] statoAbbonamentoList = StatoAbbonamento.values();
        Tipo[] tipoList = Tipo.values();
        return new Abbonamento(date, statoAbbonamentoList[r.nextInt(statoAbbonamentoList.length)], tipoList[r.nextInt(tipoList.length)]);
    };
    public static Supplier<Biglietto> bigliettoCreateOne = () -> {
        LocalDate date = fk.date().birthday(0, 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Biglietto(r.nextInt(0, 1) == 0, date);
    };

    //create Object
    public static List<PuntoDiEmissione> createPuntoDiEmissione() {
        List<PuntoDiEmissione> puntoDiEmissioneList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int numOfPuntoDiEmissione;
        while (true) {
            System.out.println("Quanti punti di emissione vuoi creare?");
            try {
                numOfPuntoDiEmissione = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }

        }

        System.out.println("1. Crea distributori automatici \n 2. Crea rivenditori autorizzati");
        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    for (int i = 0; i < numOfPuntoDiEmissione; i++) {
                        puntoDiEmissioneList.add(distributoriAutomaticiCreateOne.get());
                    }
                    System.out.println("Distributori automatici creati con successo");
                    break;
                case 2:
                    for (int i = 0; i < numOfPuntoDiEmissione; i++) {
                        puntoDiEmissioneList.add(rivenditoriAutorizzatiCreateOne.get());
                    }
                    System.out.println("Rivenditori autorizzati creati con successo");
                    break;
                default:
                    System.out.println("Scelta non valida riprova!");
                    break;
            }

        } catch (InputMismatchException e) {
            System.out.println("Inserisci un numero valido");

        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        sc.close();
        return puntoDiEmissioneList;
    }

    public static List<Utente> createUtente() {
        List<Utente> utenteList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int numOfUtente;
        while (true) {
            System.out.println("Quante persone vuoi creare?");
            try {
                numOfUtente = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            for (int i = 0; i < numOfUtente; i++) {
                utenteList.add(utenteCreateOne.get());
            }
            System.out.println("Persone create con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return utenteList;
    }

    public static List<Tessera> createTessera() {
        List<Tessera> tesseraList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int numOfTessera;
        while (true) {
            System.out.println("Quante persone vuoi creare?");
            try {
                numOfTessera = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            for (int i = 0; i < numOfTessera; i++) {
                tesseraList.add(tesseraCreateOne.get());
            }
            System.out.println("Persone create con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return tesseraList;
    }

    public static List<Abbonamento> createAbbonamento() {
        List<Abbonamento> abbonamentoList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int numOfAbbonamento;
        while (true) {
            System.out.println("quanti abbonamenti vuoi creare?");
            try {
                numOfAbbonamento = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            for (int i = 0; i < numOfAbbonamento; i++) {
                abbonamentoList.add(abbonamentoCreateOne.get());
            }
            System.out.println("Abbonamenti creati con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return abbonamentoList;
    }

    public static List<Biglietto> createBiglietto() {
        List<Biglietto> bigliettoList = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int numOfBiglietto;
        while (true) {
            System.out.println("quanti biglietti vuoi creare?");
            try {
                numOfBiglietto = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");

            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            for (int i = 0; i < numOfBiglietto; i++) {
                bigliettoList.add(bigliettoCreateOne.get());
            }
            System.out.println("Biglietti creati con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return bigliettoList;
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }

}





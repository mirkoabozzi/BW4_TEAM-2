package team_2;

import com.github.javafaker.Faker;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.dao.*;
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
    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();
    public static Supplier<DistributoriAutomatici> distributoriAutomaticiCreateOne = () -> {
        StatoDistributori[] statoDistributoriList = StatoDistributori.values();
        return new DistributoriAutomatici(statoDistributoriList[r.nextInt(statoDistributoriList.length)]);
    };

    public static Supplier<Utente> utenteCreateOne = () -> {
        LocalDate date = fk.date().birthday(12, 90).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Utente(fk.name().firstName(), fk.name().lastName(), date, r.nextInt(111111111, 999999999));
    };

    public static Supplier<Abbonamento> abbonamentoCreateOne = () -> {
        LocalDate date = fk.date().birthday(0, 10).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        StatoAbbonamento[] statoAbbonamentoList = StatoAbbonamento.values();
        Tipo[] tipoList = Tipo.values();
        return new Abbonamento(date, statoAbbonamentoList[r.nextInt(statoAbbonamentoList.length)], tipoList[r.nextInt(tipoList.length)]);
    };

    public static Tessera tesseraCreateOne(List<Utente> utenteList, List<PuntoDiEmissione> puntoDiEmissioneList) {
        LocalDate initialDate = fk.date().birthday(2, 5).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = fk.date().birthday(0, 2).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Tessera(initialDate, endDate, r.nextInt(0, 2) == 0, puntoDiEmissioneList.get(r.nextInt(puntoDiEmissioneList.size())));
    }

    public static Biglietto bigliettoCreateOne(List<Tessera> tesseraList) {
        LocalDate date = fk.date().birthday(0, 1).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return new Biglietto(r.nextInt(0, 1) == 0, date, tesseraList.get(r.nextInt(tesseraList.size())));
    }

    //create Object
    public static List<PuntoDiEmissione> createPuntoDiEmissione() {
        List<PuntoDiEmissione> puntoDiEmissioneList = new ArrayList<>();

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

        return puntoDiEmissioneList;
    }

    public static List<Utente> createUtente() {
        List<Utente> utenteList = new ArrayList<>();

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

    public static List<Tessera> createTessera(List<Utente> utenteList, List<PuntoDiEmissione> puntoDiEmissioneList) {
        List<Tessera> tesseraList = new ArrayList<>();

        int numOfTessera;
        while (true) {
            System.out.println("Quante tessere vuoi creare?");
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
                tesseraList.add(tesseraCreateOne(utenteList, puntoDiEmissioneList));
            }
            System.out.println("Tessere create con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return tesseraList;
    }

    public static List<Abbonamento> createAbbonamento() {
        List<Abbonamento> abbonamentoList = new ArrayList<>();

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
            System.out.println(abbonamentoList);
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return abbonamentoList;
    }

    public static List<Biglietto> createBiglietto(List<Tessera> tesseraList) {
        List<Biglietto> bigliettoList = new ArrayList<>();

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
                bigliettoList.add(bigliettoCreateOne(tesseraList));
            }
            System.out.println("Biglietti creati con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }

        return bigliettoList;
    }

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        //dao
        PuntoDiEmissioneDAO ped = new PuntoDiEmissioneDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        BigliettoDAO bd = new BigliettoDAO(em);
        AbbonamentoDAO ad = new AbbonamentoDAO(em);
        //Liste
        //liste
        List<PuntoDiEmissione> puntoDiEmissioneList = new ArrayList<>();
        List<Utente> utenteList = new ArrayList<>();
        List<Biglietto> bigliettoList = new ArrayList<>();
        List<Abbonamento> abbonamentoList = new ArrayList<>();
        List<Tessera> tesseraList = new ArrayList<>();

        //liste aggiornate dal db
        puntoDiEmissioneList = em.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
        utenteList = em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
        bigliettoList = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
        abbonamentoList = em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class).getResultList();
        tesseraList = em.createQuery("SELECT p FROM Tessera p", Tessera.class).getResultList();

        while (true) {
            try {
                System.out.println("Cosa vuoi creare?");
                System.out.println("1. Crea punto di emissione \n2. Salva punto di emissione  \n3. Cerca punto di emissione tramite id \n4. Elimina punto di emissione\n5. Crea utente \n6. Salva utente  \n7. Cerca utente tramite id \n8. Elimina utente\n" + "9. Crea biglietto \n10. Salva biglietto  \n11. Cerca biglietto tramite id \n12. Elimina biglietto\n13. Crea tessera \n14. Salva tessera  \n15. Cerca tessera tramite id \n16. Elimina tessera\n" + "17. Crea abbonamento \n18. Salva abbonamento  \n19. Cerca abbonamento tramite id \n20. Elimina abbonamento\n0. Esci");
                String choice = sc.nextLine();
                switch (choice) {

                    case "1":
                        puntoDiEmissioneList = createPuntoDiEmissione();
                        System.out.println(puntoDiEmissioneList);
                        break;
                    case "2":
                        ped.save(puntoDiEmissioneList);
                        break;
                    case "3":
                        try {
                            System.out.println("Quale punto di emissione vuoi cercare tramite id?");
                            String findId = sc.nextLine();
                            System.out.println(ped.getById(findId));

                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto\n");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "4":
                        try {
                            System.out.println("Quale punto di emissione vuoi eliminare tramite id?");
                            String findByIdAndDelete = sc.nextLine();
                            ped.deleteById(findByIdAndDelete);
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "5":
                        utenteList = createUtente();
                        System.out.println(utenteList);
                        break;
                    case "6":
                        ud.save(utenteList);
                        break;
                    case "7":
                        try {
                            System.out.println("Quale utente vuoi cercare tramite id?");
                            String findId = sc.nextLine();
                            System.out.println(ud.getById(findId));

                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto\n");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "8":
                        try {
                            System.out.println("Quale utente vuoi eliminare tramite id?");
                            String findByIdAndDelete = sc.nextLine();
                            ud.deleteById(findByIdAndDelete);
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "9":
                        bigliettoList = createBiglietto(tesseraList);
                        System.out.println(bigliettoList);
                        break;
                    case "10":
                        bd.save(bigliettoList);
                        break;
                    case "11":
                        try {
                            System.out.println("Quale biglietto vuoi cercare tramite id?");
                            String findId = sc.nextLine();
                            System.out.println(bd.getById(findId));

                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto\n");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "12":
                        try {
                            System.out.println("Quale biglietto vuoi eliminare tramite id?");
                            String findByIdAndDelete = sc.nextLine();
                            bd.deleteById(findByIdAndDelete);
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "13":
                        tesseraList = createTessera(utenteList, puntoDiEmissioneList);
                        System.out.println(tesseraList);
                        break;
                    case "14":
                        td.save(tesseraList);
                        break;
                    case "15":
                        try {
                            System.out.println("Quale tessera vuoi cercare tramite id?");
                            String findId = sc.nextLine();
                            System.out.println(td.getById(findId));

                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto\n");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "16":
                        try {
                            System.out.println("Quale tessera vuoi eliminare tramite id?");
                            String findByIdAndDelete = sc.nextLine();
                            td.deleteById(findByIdAndDelete);
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case "17":
                        abbonamentoList = createAbbonamento();
                        System.out.println(abbonamentoList);
                        break;
                    case "18":
                        ad.save(abbonamentoList);
                        break;
                    case "19":
                        try {
                            System.out.println("Quale abbonamento vuoi cercare tramite id?");
                            String findId = sc.nextLine();
                            System.out.println(ad.getByID(findId));

                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto\n");

                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "20":
                        try {
                            System.out.println("Quale abbonamento vuoi eliminare tramite id?");
                            String findByIdAndDelete = sc.nextLine();
                            ad.deleteById(findByIdAndDelete);
                        } catch (NumberFormatException e) {
                            System.out.println("Inserisci il formato corretto");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;

//                    case "7":
//                        users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
//                        catalogs = em.createQuery("SELECT u FROM Catalog u", Catalog.class).getResultList();
//                        loans = createLoan(users.get(r.nextInt(users.size())), catalogs.get(r.nextInt(catalogs.size())));
//                        System.out.println(loans);
//                        break;
//                    case "8":
//                        ld.save(loans);
//                        break;

                    case "0":
                        break;
                    default:
                        System.out.println("il valore non è valido");
                        break;
                }
                if (choice.equals("0")) {
                    System.out.println("Uscita dal programma...");
                    break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }


        em.close();
        emf.close();
        sc.close();
    }

}





package team_2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.dao.*;
import team_2.entities.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.StatoDistributori;
import team_2.enums.Tipo;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

    //createOne
    public static Abbonamento abbonamentoCreateOne(LocalDate date, StatoAbbonamento statoAbbonamento, Tipo tipo, Tessera tessera) {
        return new Abbonamento(date, statoAbbonamento, tipo, tessera);
    }

    ;

    public static Utente utenteCreateOne(String name, String surname, LocalDate date) {
        return new Utente(name, surname, date);
    }

    public static DistributoriAutomatici distributoriAutomaticiCreateOne(StatoDistributori statoDistributori) {
        return new DistributoriAutomatici(statoDistributori);
    }

    public static RivenditoriAutorizzati rivenditoriAutorizzatiCreateOne(String name) {
        return new RivenditoriAutorizzati(name);
    }

    public static Tessera tesseraCreateOne(LocalDate initialDate, LocalDate endDate, boolean bool, Utente utente, PuntoDiEmissione puntoDiEmissione) {
        return new Tessera(initialDate, endDate, bool, utente, puntoDiEmissione);
    }

    public static Biglietto bigliettoCreateOne(boolean vidimizzato, LocalDate date, Tessera tessera) {
        return new Biglietto(vidimizzato, date, tessera);
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

        PuntoDiEmissione puntoDiEmissione = null;
        Utente utente = null;
        Biglietto biglietto = null;
        Abbonamento abbonamento = null;
        Tessera tessera = null;
        Giro giro = null;
        Mezzo mezzo = null;
        Tratta tratta = null;
        Manutenzione manutenzione = null;


        //liste aggiornate dal db
//        puntoDiEmissioneList = em.createQuery("SELECT p FROM PuntoDiEmissione p", PuntoDiEmissione.class).getResultList();
//        utenteList = em.createQuery("SELECT u FROM Utente u", Utente.class).getResultList();
//        bigliettoList = em.createQuery("SELECT b FROM Biglietto b", Biglietto.class).getResultList();
//        abbonamentoList = em.createQuery("SELECT a FROM Abbonamento a", Abbonamento.class).getResultList();
//        tesseraList = em.createQuery("SELECT p FROM Tessera p", Tessera.class).getResultList();
//        giroList = em.createQuery("SELECT g FROM Giro g", Giro.class).getResultList();
//        mezzoList = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
//        trattaList = em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
//        //manca manutenzione

        while (true) {
            try {
                System.out.println("Cosa vuoi creare?");
                System.out.println("1. Crea punto di emissione \n2. Salva punto di emissione  \n3. Cerca punto di emissione tramite id \n4. Elimina punto di emissione\n5. Crea utente \n6. Salva utente  \n7. Cerca utente tramite id \n8. Elimina utente\n" + "9. Crea biglietto \n10. Salva biglietto  \n11. Cerca biglietto tramite id \n12. Elimina biglietto\n13. Crea tessera \n14. Salva tessera  \n15. Cerca tessera tramite id \n16. Elimina tessera\n" + "17. Crea abbonamento \n18. Salva abbonamento  \n19. Cerca abbonamento tramite id \n20. Elimina abbonamento\n0. Esci");
                String choice = sc.nextLine();
                switch (choice) {

                    case "1":
                        puntoDiEmissione = createPuntoDiEmissione();
                        System.out.println(puntoDiEmissione);
                        break;
                    case "2":
                        ped.save(puntoDiEmissione);
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
                        utente = createUtente();
                        System.out.println(utente);
                        break;
                    case "6":
                        ud.save(utente);
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
                        biglietto = createBiglietto(td);
                        System.out.println(biglietto);
                        break;
                    case "10":
                        bd.save(biglietto);
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
                        tessera = createTessera(ud, ped);
                        System.out.println(tessera);
                        break;
                    case "14":
                        td.save(tessera);
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
                        abbonamento = createAbbonamento(td);
                        System.out.println(abbonamento);
                        break;
                    case "18":
                        ad.save(abbonamento);
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

    //create Object
    public static PuntoDiEmissione createPuntoDiEmissione() {
        System.out.println("1. Crea distributori automatici \n 2. Crea rivenditori autorizzati");
        int choice;
        PuntoDiEmissione puntoDiEmissione = null;

        try {
            choice = Integer.parseInt(sc.nextLine());
            switch (choice) {
                case 1:
                    StatoDistributori statoDistributori;
                    while (true) {
                        try {
                            System.out.println("Inserisci lo stato dei distributori (ATTIVO, FUORI_SERVIZIO)");
                            statoDistributori = StatoDistributori.valueOf(sc.nextLine());
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("inserisci un numero valido");
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    System.out.println("Distributori automatici creati con successo");
                    puntoDiEmissione = distributoriAutomaticiCreateOne(statoDistributori);
                    break;
                case 2:
                    String name;
                    while (true) {
                        try {
                            System.out.println("Inserisci nome punto di emissione");
                            name = sc.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("inserisci un numero valido");
                        } catch (Exception e) {
                            System.out.println("Errore: " + e.getMessage());
                        }
                    }
                    System.out.println("Rivenditori autorizzati creati con successo");
                    puntoDiEmissione = rivenditoriAutorizzatiCreateOne(name);
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

        return puntoDiEmissione;
    }

    public static Utente createUtente() {
        Utente utente = null;
        String name;
        String surname;
        LocalDate date;
        while (true) {
            try {
                System.out.println("Inserisci nome utente");
                name = sc.nextLine();
                System.out.println("Inserisci cognome utente");
                surname = sc.nextLine();
                System.out.println("Inserisci data di nascita");
                date = LocalDate.parse(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            utente = utenteCreateOne(name, surname, date);
            System.out.println("Persone create con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return utente;
    }

    public static Tessera createTessera(UtenteDAO ud, PuntoDiEmissioneDAO ped) {
        LocalDate initialDate;
        LocalDate endDate;
        boolean bool;
        Utente utente;
        PuntoDiEmissione puntoDiEmissione;
        while (true) {
            try {
                System.out.println("Inserisci data di inizio");
                initialDate = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci data di fine");
                endDate = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci se la tessera è velida o no (true, false)");
                bool = Boolean.parseBoolean(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        Tessera tessera = null;
        try {
            System.out.println("Inserisci id Utente da abbinare alla tessera");
            String id = sc.nextLine();
            System.out.println("Inserisci id punto di emissione da abbinare alla tessera");
            String id1 = sc.nextLine();
            tessera = tesseraCreateOne(initialDate, endDate, bool, ud.getById(id), ped.getById(id1));
            System.out.println("Tessere create con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return tessera;
    }

    public static Abbonamento createAbbonamento(TesseraDAO td) {
        Abbonamento abbonamento = null;
        LocalDate date;
        StatoAbbonamento statoAbbonamento;
        Tipo tipo;
        while (true) {
            try {
                System.out.println("Inserisci data ultimo rinnovo");
                date = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci stato abbonamento(ATTIVO,NON_ATTIVO)");
                statoAbbonamento = StatoAbbonamento.valueOf(sc.nextLine());
                System.out.println("Inserisci tipo abbonamento (SETTIMANALE,MENSILE)");
                tipo = Tipo.valueOf(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("inserisci id tessera ");
            String id = sc.nextLine();
            abbonamento = abbonamentoCreateOne(date, statoAbbonamento, tipo, td.getById(id));
            System.out.println("Abbonamento creato con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return abbonamento;
    }

    public static Biglietto createBiglietto(TesseraDAO td) {
        Biglietto biglietto = null;
        boolean vidimizzato;
        LocalDate date;
        while (true) {
            try {
                System.out.println("Inserisci se il biglietto è stato vidimizzato (true,false)");
                vidimizzato = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Inserisci data di vidimizzazione");
                date = LocalDate.parse(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("inserisci id tessera ");
            String id = sc.nextLine();
            biglietto = bigliettoCreateOne(vidimizzato, date, td.getById(id));
            System.out.println("Biglietti creati con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return biglietto;
    }

}





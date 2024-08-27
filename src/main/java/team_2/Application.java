package team_2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.dao.*;
import team_2.entities.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.StatoDistributori;
import team_2.enums.Tipo;
import team_2.enums.TipoMezzo;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
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

    public static Giro giroCreateOne(Tratta tratta, Mezzo mezzo) {
        return new Giro(tratta, mezzo);
    }

    public static Tratta trattaCreateOne(String zanaPartenza, String capolinea, double tempoPercorrenzaPrevisto, double tempoPercorrenzaEffettivo, double orarioPartenza, List<Mezzo> mezzoList) {
        return new Tratta(zanaPartenza, capolinea, tempoPercorrenzaPrevisto, tempoPercorrenzaEffettivo, orarioPartenza, mezzoList);
    }

    public static Mezzo mezzoCreateOne(TipoMezzo tipoMezzo, int capienza, boolean inServizio, LocalDate dataInizioServizio, LocalDate dataFineServizio, int numeroMezzo) {
        return new Mezzo(tipoMezzo, capienza, inServizio, dataInizioServizio, dataFineServizio, numeroMezzo);
    }

    public static Manutenzione manutenzioneCreateOne(TipoMezzo tipoMezzo, LocalDate dataInizio, LocalDate dataFine, Mezzo mezzo) {
        return new Manutenzione(tipoMezzo, dataInizio, dataFine, mezzo);
    }

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();
        //dao
        PuntoDiEmissioneDAO ped = new PuntoDiEmissioneDAO(em);
        UtenteDAO ud = new UtenteDAO(em);
        TesseraDAO td = new TesseraDAO(em);
        BigliettoDAO bd = new BigliettoDAO(em);
        AbbonamentoDAO ad = new AbbonamentoDAO(em);
        GiroDAO gd = new GiroDAO(em);
        MezzoDAO md = new MezzoDAO(em);
        TrattaDAO trd = new TrattaDAO(em);
        ManutenzioneDAO mnd = new ManutenzioneDAO(em);
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

//        trattaList = em.createQuery("SELECT t FROM Tratta t", Tratta.class).getResultList();
//        manutenzioneList = em.createQuery("SELECT m FROM Manutenzione m", Manutenzione.class).getResultList();

        try {
            for (int i = 0; i < 1; i++) {
                System.out.println("1. Amministratore\n2. Utente");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("inserisci password");
                        String pass = sc.nextLine();
                        if (pass.equals("1234")) {
                            System.out.println("ok");
                            while (true) {
                                try {
                                    System.out.println("Cosa vuoi creare?");
                                    System.out.println("""
                                            1. Crea punto di emissione\s
                                            2. Cerca punto di emissione tramite id\s
                                            3. Elimina punto di emissione
                                            4. Crea utente\s
                                            5. Cerca utente tramite id\s
                                            6. Elimina utente
                                            7. Crea tessera\s
                                            8. Cerca tessera tramite id\s
                                            9. Elimina tessera
                                            10. Crea mezzo\s
                                            11. Cerca mezzo tramite id\s
                                            12. Elimina mezzo
                                            13. Crea tratta\s
                                            14. Cerca tratta tramite id\s
                                            15. Elimina tratta
                                            16. Crea giro\s
                                            17. Cerca giro tramite id\s
                                            18. Elimina giro
                                            19. Crea manutenzione\s
                                            20. Cerca manutenzione tramite id\s
                                            21. Elimina manutenzione
                                            0. Esci""");
                                    String choice1 = sc.nextLine();
                                    switch (choice1) {

                                        case "1":
                                            puntoDiEmissione = createPuntoDiEmissione();
                                            System.out.println(puntoDiEmissione);
                                            ped.save(puntoDiEmissione);
                                            break;
                                        case "2":
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
                                        case "3":
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
                                        case "4":
                                            utente = createUtente();
                                            System.out.println(utente);
                                            ud.save(utente);
                                            break;
                                        case "5":
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
                                        case "6":
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

                                        case "7":
                                            tessera = createTessera(ud, ped);
                                            System.out.println(tessera);
                                            td.save(tessera);
                                            break;
                                        case "8":
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
                                        case "9":
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

                                        case "10":
                                            mezzo = createMezzo();
                                            System.out.println(mezzo);
                                            md.save(mezzo);
                                            break;
                                        case "11":
                                            try {
                                                System.out.println("Quale mezzo vuoi cercare tramite id?");
                                                String findId = sc.nextLine();
                                                System.out.println(md.getById(findId));

                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto\n");

                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "12":
                                            try {
                                                System.out.println("Quale mezzo vuoi eliminare tramite id?");
                                                String findByIdAndDelete = sc.nextLine();
                                                md.deleteById(findByIdAndDelete);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;

                                        case "13":
                                            List<Mezzo> mezzoList = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
                                            tratta = createTratta(mezzoList);
                                            System.out.println(tratta);
                                            trd.save(tratta);
                                            break;

                                        case "14":
                                            try {
                                                System.out.println("Quale tratta vuoi cercare tramite id?");
                                                String findId = sc.nextLine();
                                                System.out.println(trd.getByID(findId));

                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto\n");

                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "15":
                                            try {
                                                System.out.println("Quale tratta vuoi eliminare tramite id?");
                                                String findByIdAndDelete = sc.nextLine();
                                                trd.deleteById(findByIdAndDelete);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "16":
                                            giro = createGiro(trd, md);
                                            System.out.println(giro);
                                            gd.save(giro);
                                            break;
                                        case "17":
                                            try {
                                                System.out.println("Quale giro vuoi cercare tramite id?");
                                                String findId = sc.nextLine();
                                                System.out.println(gd.getByID(findId));

                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto\n");

                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "18":
                                            try {
                                                System.out.println("Quale giro vuoi eliminare tramite id?");
                                                String findByIdAndDelete = sc.nextLine();
                                                gd.deleteById(findByIdAndDelete);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;

                                        case "19":
                                            manutenzione = createManutenzione(md);
                                            System.out.println(manutenzione);
                                            mnd.save(manutenzione);
                                            break;

                                        case "20":
                                            try {
                                                System.out.println("Quale manutenzione vuoi cercare tramite id?");
                                                String findId = sc.nextLine();
                                                System.out.println(mnd.getByID(findId));

                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto\n");

                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "21":
                                            try {
                                                System.out.println("Quale manutenzione vuoi eliminare tramite id?");
                                                String findByIdAndDelete = sc.nextLine();
                                                mnd.deleteById(findByIdAndDelete);
                                            } catch (NumberFormatException e) {
                                                System.out.println("Inserisci il formato corretto");
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                            break;
                                        case "0":
                                            System.out.println("Uscita dal programma...");
                                            return;
                                        default:
                                            System.out.println("il valore non è valido");
                                            break;
                                    }

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                            }
                        } else {
                            i--;
                        }
                        break;
                    case "2":
                        while (true) {
                            try {
                                System.out.println("Cosa vuoi creare?");
                                System.out.println("""
                                        1. Crea biglietto\s
                                        2. Cerca biglietto tramite id\s
                                        3. Elimina biglietto
                                        4. Crea abbonamento\s
                                        5. Cerca abbonamento tramite id\s
                                        6. Elimina abbonamento
                                        0. Esci""");
                                String choice2 = sc.nextLine();
                                switch (choice2) {
                                    case "1":
                                        biglietto = createBiglietto(td);
                                        System.out.println(biglietto);
                                        bd.save(biglietto);
                                        break;
                                    case "2":
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
                                    case "3":
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

                                    case "4":
                                        abbonamento = createAbbonamento(td);
                                        System.out.println(abbonamento);
                                        ad.save(abbonamento);
                                        break;
                                    case "5":
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
                                    case "6":
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
                                    case "0":
                                        System.out.println("Uscita del programma in corso...");
                                        return;
                                    default:
                                        System.out.println("il valore non è valido");
                                        break;
                                }

                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }

                        }


                    default:
                        System.out.println("scelta non valida riprova");
                        i--;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
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

    public static Giro createGiro(TrattaDAO td, MezzoDAO md) {
        Giro giro = null;
        try {
            System.out.println("inserisci id tratta ");
            String id = sc.nextLine();
            System.out.println("inserisci id mezzo ");
            String id1 = sc.nextLine();
            giro = giroCreateOne(td.getByID(id), md.getById(id1));
            System.out.println("Giro creato con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return giro;
    }

    public static Tratta createTratta(List<Mezzo> mezzoList) {
        Tratta tratta = null;
        String zanaPartenza;
        String capolinea;
        double tempoPercorrenzaPrevisto;
        double tempoPercorrenzaEffettivo;
        double orarioPartenza;

        while (true) {
            try {
                System.out.println("Inserisci zona partenza");
                zanaPartenza = sc.nextLine();
                System.out.println("Inserisci capolinea");
                capolinea = sc.nextLine();
                System.out.println("Inserisci tempo percorrenza previsto");
                tempoPercorrenzaPrevisto = Double.parseDouble(sc.nextLine());
                System.out.println("Inserisci tempo percorrenza effettivo");
                tempoPercorrenzaEffettivo = Double.parseDouble(sc.nextLine());
                System.out.println("Inserisci orario di partenza");
                orarioPartenza = Double.parseDouble(sc.nextLine());

                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            tratta = trattaCreateOne(zanaPartenza, capolinea, tempoPercorrenzaPrevisto, tempoPercorrenzaEffettivo, orarioPartenza, mezzoList);
            System.out.println("Tratta creata con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return tratta;
    }

    public static Mezzo createMezzo() {
        Mezzo mezzo = null;
        TipoMezzo tipoMezzo;
        int capienza;
        boolean inServizio;
        LocalDate dataInizioServizio;
        LocalDate dataFineServizio;
        int numeroMezzo;

        while (true) {
            try {
                System.out.println("Inserisci tipo mezzo(AUTOBUS, TRAM)");
                tipoMezzo = TipoMezzo.valueOf(sc.nextLine());
                System.out.println("Inserisci capienza");
                capienza = Integer.parseInt(sc.nextLine());
                System.out.println("Inserisci se il mezzo è in servizio (true,false)");
                inServizio = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Inserisci data inizio servizio");
                dataInizioServizio = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci data fine servizio");
                dataFineServizio = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci numero mezzo");
                numeroMezzo = Integer.parseInt(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            mezzo = mezzoCreateOne(tipoMezzo, capienza, inServizio, dataInizioServizio, dataFineServizio, numeroMezzo);
            System.out.println("Mezzo creato con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return mezzo;
    }

    public static Manutenzione createManutenzione(MezzoDAO md) {
        Manutenzione manutenzione = null;
        TipoMezzo tipoMezzo;
        LocalDate dataInizio;
        LocalDate dataFine;
        while (true) {
            try {
                System.out.println("Inserisci tipo mezzo(AUTOBUS, TRAM)");
                tipoMezzo = TipoMezzo.valueOf(sc.nextLine());
                System.out.println("Inserisci data inizio manutenzione");
                dataInizio = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci data fine manutenzione");
                dataFine = LocalDate.parse(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("inserisci id mezzo ");
            String id = sc.nextLine();
            manutenzione = manutenzioneCreateOne(tipoMezzo, dataInizio, dataFine, md.getById(id));
            System.out.println("Manutenzione creata con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return manutenzione;
    }


}





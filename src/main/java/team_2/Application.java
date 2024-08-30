package team_2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import team_2.dao.*;
import team_2.entities.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.StatoDistributori;
import team_2.enums.TipoAbbonamento;
import team_2.enums.TipoMezzo;
import team_2.exceptions.NotFoundException;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
    static Scanner sc = new Scanner(System.in);

    //createOne
    public static Abbonamento abbonamentoCreateOne(LocalDate date, StatoAbbonamento statoAbbonamento, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        return new Abbonamento(date, statoAbbonamento, tipoAbbonamento, tessera);
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

    public static Tessera tesseraCreateOne(LocalDate initialDate, LocalDate endDate, boolean bool, Utente utente, PuntoDiEmissione puntoDiEmissione, Giro giroId) {
        return new Tessera(initialDate, endDate, bool, utente, puntoDiEmissione, giroId);
    }

    public static Biglietto bigliettoCreateOne(boolean vidimato, LocalDate date, Tessera tessera) {
        return new Biglietto(vidimato, date, tessera);
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


        PuntoDiEmissione puntoDiEmissione = null;
        Utente utente = null;
        Biglietto biglietto = null;
        Abbonamento abbonamento = null;
        Tessera tessera = null;
        Giro giro = null;
        Mezzo mezzo = null;
        Tratta tratta = null;
        Manutenzione manutenzione = null;


        try {
            while (true) {
                System.out.println("1. Amministratore\n2. Utente\n0. Esci dal programma");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Inserisci password");
                        String pass = sc.nextLine();
                        if (pass.equals("1234")) {
                            while (true) {

                                System.out.println("1. Crea\n2. Elimina o modifica\n3. Menu avanzato\n0. Torna al menu precedente");
                                String scelta = sc.nextLine();
                                switch (scelta) {
                                    case "1" -> {
                                        System.out.println("Cosa vuoi creare?");
                                        while (true) {
                                            try {
                                                System.out.println("""
                                                        1. Crea punto di emissione\s
                                                        2. Crea utente\s
                                                        3. Crea tessera\s
                                                        4. Crea mezzo\s
                                                        5. Crea tratta\s
                                                        6. Crea giro\s
                                                        7. Crea manutenzione\s
                                                        0. Torna al menu precedente""");
                                                String sceltaCrea = sc.nextLine();
                                                switch (sceltaCrea) {
                                                    case "1":
                                                        puntoDiEmissione = createPuntoDiEmissione();
                                                        System.out.println(puntoDiEmissione);
                                                        ped.save(puntoDiEmissione);
                                                        break;
                                                    case "2":
                                                        utente = createUtente();
                                                        System.out.println(utente);
                                                        ud.save(utente);
                                                        break;
                                                    case "3":
                                                        tessera = createTessera(ud, ped, gd);
                                                        System.out.println(tessera);
                                                        td.save(tessera);
                                                        break;
                                                    case "4":
                                                        mezzo = createMezzo();
                                                        System.out.println(mezzo);
                                                        md.save(mezzo);
                                                        break;
                                                    case "5":
                                                        List<Mezzo> mezzoList = em.createQuery("SELECT m FROM Mezzo m", Mezzo.class).getResultList();
                                                        tratta = createTratta(mezzoList);
                                                        System.out.println(tratta);
                                                        trd.save(tratta);
                                                        break;
                                                    case "6":
                                                        giro = createGiro(trd, md);
                                                        System.out.println(giro);
                                                        gd.save(giro);
                                                        break;
                                                    case "7":
                                                        manutenzione = createManutenzione(md);
                                                        System.out.println(manutenzione);
                                                        mnd.save(manutenzione);
                                                        break;
                                                    case "0":
                                                        System.out.println("Torna al menu principale");
                                                        break;
                                                    default:
                                                        System.out.println("Il valore non è valido");
                                                        break;
                                                }
                                                if (sceltaCrea.equals("0")) break;
                                            } catch (Exception e) {
                                                System.out.println(e.getMessage());
                                            }
                                        }
                                    }
                                    case "2" -> {
                                        System.out.println("Cosa vuoi eliminare o modificare?");
                                        while (true) {
                                            try {
                                                System.out.println("""
                                                        1. Elimina punto di emissione
                                                        2. Elimina utente
                                                        3. Elimina tessera
                                                        4. Elimina mezzo
                                                        5. Elimina tratta
                                                        6. Elimina giro
                                                        7. Elimina manutenzione
                                                        8. Aggiorna / modifica data termine manutenzione
                                                        0. Torna al menu precedente""");
                                                String sceltaElimina = sc.nextLine();
                                                switch (sceltaElimina) {
                                                    case "1":
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
                                                    case "2":
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
                                                    case "3":
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
                                                    case "4":
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
                                                    case "5":
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
                                                    case "6":
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
                                                    case "7":
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
                                                    case "8":
                                                        try {
                                                            System.out.println("Inserisci un id manutenzione valida");
                                                            String idMezzo = sc.nextLine();
                                                            System.out.println("Inserisci la data di rinnovo");
                                                            String data = sc.nextLine();
                                                            mnd.estendiFineManutenzione(UUID.fromString(idMezzo), LocalDate.parse(data));
                                                        } catch (Exception ex) {
                                                            System.out.println("Input non valido " + ex.getMessage());
                                                        }
                                                        break;
                                                    case "0":
                                                        System.out.println("Torna al menu principale");
                                                        break;
                                                    default:
                                                        System.out.println("Il valore non è valido");
                                                        break;
                                                }
                                                if (sceltaElimina.equals("0")) break;
                                            } catch (Exception ex) {
                                                System.out.println(ex.getMessage());
                                            }
                                        }
                                    }
                                    case "3" -> {
                                        System.out.println("Menu avanzato");

                                        while (true) {
                                            try {
                                                System.out.println("""
                                                        1. Gestione punti Emissione
                                                        2. Gestione utenti
                                                        3. Gestione tessere
                                                        4. Gestione mezzi
                                                        0. Torna al menu precedente""");
                                                String menuAvanzato = sc.nextLine();
                                                switch (menuAvanzato) {
                                                    case "1":
                                                        System.out.println("Gestione punti emissione");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                        1. Cerca punto di emissione tramite id
                                                                        2. Ottenere tessere per un determinato punto di emissione
                                                                        3. Calcola quanti punti di Emissione ci sono
                                                                        4. Calcola quanti Rivenditori Autorizzati ci sono
                                                                        5. Calcola quanti Distributori Automatici ci sono
                                                                        6. Trova i Rivenditori Autorizzati tramite nome
                                                                        7. Calcola quanti Distributori Automatici hanno stato Attivo
                                                                        0. Torna al menu principale""");
                                                                String gestionePuntiEmissione = sc.nextLine();
                                                                switch (gestionePuntiEmissione) {
                                                                    case "1":
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
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci punto di Emissione:");
                                                                            UUID puntoDiEmissioneId = UUID.fromString(sc.nextLine());
                                                                            List<Tessera> tessere = td.trovaTesserePerPuntoDiEmissione(puntoDiEmissioneId);
                                                                            System.out.println("Tessere trovate: " + tessere.toString());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Totale Punti di emissione: " + ped.contaPuntiDiEmissione());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            System.out.println("Totale Rivenditori Autorizzati: " + ped.contaRivenditoriAutorizzati());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println("Totale Distributori Automatici: " + ped.contaDistributoriAutomatici());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "6":
                                                                        try {
                                                                            System.out.println("Inserisci il nome di un Rivenditore:");
                                                                            String name = sc.nextLine();
                                                                            List<RivenditoriAutorizzati> rivenditori = ped.cercaRivenditoriAutorizzatiPerNome(name);
                                                                            rivenditori.forEach(System.out::println);
                                                                            System.out.println("Rivenditori trovati: " + rivenditori.size());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "7":
                                                                        try {
                                                                            System.out.println("Totale Distributori Automatici attivi: " + ped.DistributoriAutomaticiAttivi());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestionePuntiEmissione.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "2":
                                                        System.out.println("Gestione utenti");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                        1. Cerca utente tramite id
                                                                        2. Cerca utenti per nome.
                                                                        3. Cerca utenti per cognome.
                                                                        4. Cerca utenti per nome e cognome.
                                                                        5. Filtra utenti con età maggiore di
                                                                        6. Cerca utenti tramite numero tessera.
                                                                        7. Cerca utenti tramite id tessera.
                                                                        8. Mostra tutti gli utenti.
                                                                        0. Torna al menu precedente""");
                                                                String gestioneUtenti = sc.nextLine();
                                                                switch (gestioneUtenti) {
                                                                    case "1":
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
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci il nome da cercare:");
                                                                            String nome = sc.nextLine();
                                                                            List<Utente> utentiPerNome = ud.getUtentiByNome(nome);
                                                                            utentiPerNome.forEach(System.out::println);
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante la ricerca degli utenti per nome: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Inserisci il cognome da cercare:");
                                                                            String cognome = sc.nextLine();
                                                                            List<Utente> utentiPerCognome = ud.getUtentiByCognome(cognome);
                                                                            utentiPerCognome.forEach(System.out::println);
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante la ricerca degli utenti per cognome: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            System.out.println("Inserisci il nome da cercare:");
                                                                            String nomeCognomeNome = sc.nextLine();
                                                                            System.out.println("Inserisci il cognome da cercare:");
                                                                            String nomeCognomeCognome = sc.nextLine();
                                                                            List<Utente> utentiPerNomeECognome = ud.getUtentiByNomeECognome(nomeCognomeNome, nomeCognomeCognome);
                                                                            utentiPerNomeECognome.forEach(System.out::println);
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante la ricerca degli utenti per nome e cognome: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println("Inserisci l'età minima:");
                                                                            int eta = Integer.parseInt(sc.nextLine());
                                                                            List<Utente> utentiPerEta = ud.getUtentiByEtaMaggioreDi(eta);
                                                                            utentiPerEta.forEach(System.out::println);
                                                                        } catch (NumberFormatException e) {
                                                                            System.out.println("Inserisci un'età valida (numero intero).");
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante il filtraggio degli utenti per età: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "6":
                                                                        try {
                                                                            System.out.println("Inserisci il numero tessera da cercare:");
                                                                            int numeroTessera = Integer.parseInt(sc.nextLine());
                                                                            List<Utente> utentiPerNumeroTessera = ud.getUtentiByNumeroTessera(numeroTessera);
                                                                            utentiPerNumeroTessera.forEach(System.out::println);
                                                                        } catch (NumberFormatException e) {
                                                                            System.out.println("Inserisci un numero tessera valido (numero intero).");
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante la ricerca degli utenti per numero tessera: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "7":
                                                                        try {
                                                                            System.out.println("Inserisci l'id della tessera:");
                                                                            UUID tesseraId = UUID.fromString(sc.nextLine());
                                                                            List<Utente> utentiPerTesseraId = ud.getUtentiByTesseraId(tesseraId);
                                                                            utentiPerTesseraId.forEach(System.out::println);
                                                                        } catch (IllegalArgumentException e) {
                                                                            System.out.println("Formato ID tessera non valido. Riprova.");
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante la ricerca degli utenti per ID tessera: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "8":
                                                                        try {
                                                                            System.out.println("Elenco di tutti gli utenti:");
                                                                            List<Utente> allUtenti = ud.getAllUtenti();
                                                                            allUtenti.forEach(System.out::println);
                                                                        } catch (Exception e) {
                                                                            System.out.println("Errore durante il recupero degli utenti: " + e.getMessage());
                                                                        }
                                                                        break;
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestioneUtenti.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "3":
                                                        System.out.println("Gestione tessere");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                         1. Cerca tessera tramite id
                                                                         2. Filtra abbonamenti tramite id tessera
                                                                         3. Trova biglietti tramite id tessera
                                                                         4. Verifica se la tessera è valida
                                                                         5. Contare numero tessere valide
                                                                         6. Trovare tutte le tessere scadute
                                                                         7. Trovare tessere con una data di inizio specifica
                                                                         8. Trova le tessere associate al giro
                                                                         9. Conta i biglietti associati a una determinata tessera.
                                                                         0. Torna al menu precedente
                                                                        """);
                                                                String gestioneTessere = sc.nextLine();
                                                                switch (gestioneTessere) {
                                                                    case "1":
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
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci un ID tessera valido");
                                                                            String numeroTessera = sc.nextLine();
                                                                            ad.trovaAbbonamentiTramiteTessera(UUID.fromString(numeroTessera)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Inserisci un Id tessera valido");
                                                                            String idTessera = sc.nextLine();
                                                                            bd.trovaBigliettiTramiteIdTessera(UUID.fromString(idTessera)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            System.out.println("Inserisci un id della tessera");
                                                                            String idTessera = sc.nextLine();
                                                                            td.validitàTessera(UUID.fromString(idTessera)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println("Totale tessere valide: " + td.contaTessereValide());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "6":
                                                                        try {
                                                                            System.out.println("Inserisci giorni da oggi per cercare tessere scadute:");
                                                                            int giorni = Integer.parseInt(sc.nextLine());
                                                                            LocalDate dataCorrente = LocalDate.now().minusDays(giorni);
                                                                            List<Tessera> tessereScadute = td.trovaTessereScadute(dataCorrente);
                                                                            tessereScadute.forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "7":
                                                                        try {
                                                                            System.out.println("Inserisci la data di inizio");
                                                                            String dataInizio = sc.nextLine();
                                                                            td.trovaTesserePerDataInizio(LocalDate.parse(dataInizio)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "8":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del giro per trovare le tessere associate:");
                                                                            String giroId = sc.nextLine();
                                                                            gd.trovaTesserePerGiro(UUID.fromString(giroId)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "9":
                                                                        try {
                                                                            System.out.println("Inserisci ID tessera");
                                                                            String tesseraIdInput = sc.nextLine();
                                                                            UUID idTessera = UUID.fromString(tesseraIdInput);
                                                                            long ticketCount = td.contaBigliettiAssociati(idTessera);
                                                                            System.out.println("Numero di biglietti associati alla tessera con id " + idTessera + ": " + ticketCount);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestioneTessere.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "4":
                                                        System.out.println("Gestione mezzi");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                        1. Cerca mezzo tramite id
                                                                        2. Filtra mezzi per tipo
                                                                        3. Conta mezzi in manutenzione
                                                                        4. Conta mezzi in manutenzione per tipo
                                                                        5. Trova capienza del mezzo tramite id
                                                                        6. Verifica se il mezzo è in servizio
                                                                        7. Trova la data di inizio servizio del mezzo
                                                                        8. Trova la data di fine servizio del mezzo
                                                                        9. Trova il numero del mezzo tramite id
                                                                        10. Trova il tipo del mezzo
                                                                        11. Trova tutte le manutenzioni ricevute dal mezzo
                                                                        12. Trova il mezzo che sta facendo il giro
                                                                        13. Differenza manutenzioni per tipo mezzo
                                                                        14. Cerca manutenzione tramite id\s
                                                                        0. Torna al menu precedente
                                                                        """);
                                                                String gestioneMezzi = sc.nextLine();
                                                                switch (gestioneMezzi) {
                                                                    case "1":
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
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci un tipo di mezzo valido AUTOBUS o TRAM");
                                                                            String tipo = sc.nextLine().toUpperCase();
                                                                            mnd.filtraMezziPerTipo(TipoMezzo.valueOf(tipo)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Mezzi ancora in manutenzione");
                                                                            mnd.mezziInManutenzione().forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            System.out.println("Inserisci un tipo mezzo valido AUTOBUS TRAM");
                                                                            String tipo = sc.nextLine().toUpperCase();
                                                                            System.out.println("Mezzi di tipo " + tipo + " presenti in officina " + mnd.contaMezziInManutenzionePerTipo(TipoMezzo.valueOf(tipo)));
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare la capienza:");
                                                                            String mezzoId = sc.nextLine();
                                                                            int capienza = md.trovaCapienzaMezzo(UUID.fromString(mezzoId));
                                                                            System.out.println("Capienza del mezzo con id " + mezzoId + ": " + capienza);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "6":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per verificare se è in servizio:");
                                                                            String mezzoId = sc.nextLine();
                                                                            boolean inServizio = md.trovaStatoInServizioMezzo(UUID.fromString(mezzoId));
                                                                            System.out.println("Il mezzo con id " + mezzoId + " è " + (inServizio ? "in servizio" : "fuori servizio"));
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "7":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare la data di inizio servizio:");
                                                                            String mezzoId = sc.nextLine();
                                                                            LocalDate dataInizio = md.trovaDataInizioServizio(UUID.fromString(mezzoId));
                                                                            System.out.println("Data di inizio servizio del mezzo con id " + mezzoId + ": " + dataInizio);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "8":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare la data di fine servizio:");
                                                                            String mezzoId = sc.nextLine();
                                                                            LocalDate dataFine = md.trovaDataFineServizio(UUID.fromString(mezzoId));
                                                                            System.out.println("Data di fine servizio del mezzo con id " + mezzoId + ": " + dataFine);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "9":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare il numero del mezzo:");
                                                                            String mezzoId = sc.nextLine();
                                                                            int numeroMezzo = md.trovaNumeroMezzo(UUID.fromString(mezzoId));
                                                                            System.out.println("Numero del mezzo con id " + mezzoId + ": " + numeroMezzo);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "10":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare il tipo del mezzo:");
                                                                            String mezzoId = sc.nextLine();
                                                                            TipoMezzo tipoMezzo = md.trovaTipoMezzo(UUID.fromString(mezzoId));
                                                                            System.out.println("Tipo del mezzo con id " + mezzoId + ": " + tipoMezzo);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "11":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del mezzo per trovare tutte le manutenzioni ricevute:");
                                                                            String mezzoId = sc.nextLine();
                                                                            md.trovaManutenzioniPerMezzo(UUID.fromString(mezzoId)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "12":
                                                                        try {
                                                                            System.out.println("Inserisci l'id del giro per trovare il mezzo che sta facendo il giro:");
                                                                            String giroId = sc.nextLine();
                                                                            Mezzo mezzo1 = gd.trovaMezzoPerGiro(UUID.fromString(giroId));
                                                                            System.out.println("Mezzo che sta facendo il giro con id " + giroId + ": " + mezzo1);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "13":
                                                                        try {
                                                                            mnd.differenzaMezziInManutenzionePerTipo();
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "14":
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
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestioneMezzi.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "5":
                                                        System.out.println("Gestione abbonamenti");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                        1. Filtra abbonamenti per stato
                                                                        2. Filtra abbonamenti per data di rinnovo
                                                                        3. Filtra abbonamenti per tipo
                                                                        4. Conta abbonamenti per tipo
                                                                        5. Conta abbonamenti per stato
                                                                        6. Verifica abbonamenti in scadenza entro tot giorni
                                                                        7. Differenza abbonamenti per tipo
                                                                        0. Torna al menu precedente
                                                                        """);
                                                                String gestioneAbbonamenti = sc.nextLine();
                                                                switch (gestioneAbbonamenti) {
                                                                    case "1":
                                                                        try {
                                                                            System.out.println("Vuoi filtrare gli abbonamenti ATTIVO o NON_ATTIVO?");
                                                                            String statoAbbonamento = sc.nextLine().toUpperCase();
                                                                            ad.filtraAbbonamentiPerStato(StatoAbbonamento.valueOf(statoAbbonamento)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci la data per filtrare gli abbonamenti");
                                                                            String data = sc.nextLine();
                                                                            ad.filtraAbbonamentiRinnovatiInData(data).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Inserisci il tipo di abbonamento (MENSILE o SETTIMANALE)");
                                                                            String tipoAbbonamento = sc.nextLine().toUpperCase();
                                                                            ad.filtraAbbonamentiPerTipo(TipoAbbonamento.valueOf(tipoAbbonamento)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            System.out.println("Inserisci il tipo SETTIMANALE o MENSILE");
                                                                            String tipo = sc.nextLine().toUpperCase();
                                                                            System.out.println("Abbonamenti di tipo " + tipo + " trovati: " + ad.contaAbbonamentiPerTipo(TipoAbbonamento.valueOf(tipo)));
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println("Inserisci uno stato ATTIVO o NON_ATTIVO");
                                                                            String stato = sc.nextLine().toUpperCase();
                                                                            System.out.println("Abbonamenti " + stato + " trovati: " + ad.contaAbbonamentiPerStato(StatoAbbonamento.valueOf(stato)));
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "6":
                                                                        try {
                                                                            System.out.println("Specifica il numero giorni");
                                                                            String giorni = sc.nextLine();
                                                                            ad.abbonamentiAttiviInScadenzaEntroGiorni(Integer.parseInt(giorni)).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "7":
                                                                        try {
                                                                            ad.differenzaAbbonamentiAttiviNonAttivi();
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestioneAbbonamenti.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "6":
                                                        System.out.println("Gestione biglietti");
                                                        while (true) {
                                                            try {
                                                                System.out.println("""
                                                                        1. Filtra biglietti per stato
                                                                        2. Filtra biglietti per data vidimazione
                                                                        3. Conta biglietti vidimati o non vidimati
                                                                        4. Differenza biglietti per stato
                                                                        5. Trova la tratta con il numero più alto di biglietti vidimati
                                                                        0. Torna al menu precedente
                                                                        """);
                                                                String gestioneBiglietti = sc.nextLine();
                                                                switch (gestioneBiglietti) {
                                                                    case "1":
                                                                        try {
                                                                            System.out.println("Inserisci true per i biglietti vidimati, false per i non vidimati");
                                                                            Boolean stato = Boolean.valueOf(sc.nextLine());
                                                                            bd.filtraBigliettiPerStato(stato).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "2":
                                                                        try {
                                                                            System.out.println("Inserisci una data valida");
                                                                            String data = sc.nextLine();
                                                                            bd.filtraBigliettiVidimatiInData(data).forEach(System.out::println);
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "3":
                                                                        try {
                                                                            System.out.println("Quali biglietti vuoi contare? true vidimati / false non vidimati");
                                                                            Boolean stato = Boolean.valueOf(sc.nextLine());
                                                                            System.out.println("Biglietti trovati " + bd.contaBigliettiVidimati(stato));
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "4":
                                                                        try {
                                                                            bd.differenzaBigliettiPerVidimazione();
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Input non valido " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "5":
                                                                        try {
                                                                            System.out.println(trd.findTrattaConIlNumeroPiuAltoDiBigliettiVidimati());
                                                                        } catch (Exception ex) {
                                                                            System.out.println("Errore: " + ex.getMessage());
                                                                        }
                                                                        break;
                                                                    case "0":
                                                                        System.out.println("Torna al menu principale");
                                                                        break;
                                                                    default:
                                                                        System.out.println("il valore non è valido");
                                                                        break;
                                                                }
                                                                if (gestioneBiglietti.equals("0")) break;
                                                            } catch (Exception ex) {
                                                                System.out.println(ex.getMessage());
                                                            }
                                                        }
                                                        break;
                                                    case "0":
                                                        System.out.println("Torna al menu precedente");
                                                        break;
                                                    default:
                                                        System.out.println("il valore non è valido");
                                                        break;
                                                }
                                                if (menuAvanzato.equals("0")) break;
                                            } catch (Exception ex) {
                                                System.out.println(ex.getMessage());
                                            }
                                        }

                                    }
                                    case "0" -> System.out.println("Torno al menu precedente...");
                                    default -> System.out.println("Scelta non valida");
                                }
                                if (scelta.equals("0")) break;
                            }
                        } else System.out.println("Password errata");
                        break;
                    case "2":
                        while (true) {
                            try {
                                System.out.println("Cosa vuoi creare?");
                                System.out.println("""
                                        1. Crea biglietto
                                        2. Cerca biglietto tramite id
                                        3. Elimina biglietto
                                        4. Crea abbonamento
                                        5. Cerca abbonamento tramite id
                                        6. Elimina abbonamento
                                        7. Rinnova abbonamento
                                        8. Modifica nome utente
                                        9. Modifica cognome utente
                                        10. Modifica data di nascita utente
                                        11. Verifica se hai un abbonamento attivo
                                        12. Gestisci tratte
                                        0. Torna al menu principale""");
                                String sceltaCrea = sc.nextLine();
                                switch (sceltaCrea) {
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
                                    case "7":
                                        try {
                                            System.out.println("Inserisci un id abbonamento valido");
                                            String idAbbonamento = sc.nextLine();
                                            System.out.println("Inserisci la data di rinnovo");
                                            String data = sc.nextLine();
                                            ad.rinnovaAbbonamentoEAttiva(UUID.fromString(idAbbonamento), LocalDate.parse(data));
                                        } catch (Exception ex) {
                                            System.out.println("Input non valido " + ex.getMessage());
                                        }
                                        break;
                                    case "8":
                                        try {
                                            System.out.println("Inserisci un id utente valido");
                                            String idUtente = sc.nextLine();
                                            System.out.println("Inserisci il nuovo nome");
                                            String newName = sc.nextLine();
                                            ud.aggiornaNomeUtente(UUID.fromString(idUtente), newName);
                                        } catch (Exception ex) {
                                            System.out.println("Input non valido " + ex.getMessage());
                                        }
                                        break;
                                    case "9":
                                        try {
                                            System.out.println("Inserisci un id utente valido");
                                            String idUtente = sc.nextLine();
                                            System.out.println("Inserisci il nuovo cognome");
                                            String newSurname = sc.nextLine();
                                            ud.aggiornaCognomeUtente(UUID.fromString(idUtente), newSurname);
                                        } catch (Exception ex) {
                                            System.out.println("Input non valido " + ex.getMessage());
                                        }
                                        break;
                                    case "10":
                                        try {
                                            System.out.println("Inserisci un id utente valido");
                                            String idUtente = sc.nextLine();
                                            System.out.println("Inserisci la nuova data di nascita");
                                            LocalDate newData = LocalDate.parse(sc.nextLine());
                                            ud.aggiornaDataDiNascitaUtente(UUID.fromString(idUtente), newData);
                                        } catch (Exception ex) {
                                            System.out.println("Input non valido " + ex.getMessage());
                                        }
                                        break;
                                    case "11":
                                        try {
                                            System.out.println("Inserisci un id utente valido");
                                            String idUtente = sc.nextLine();
                                            boolean isActive = ud.hasAbbonamentoAttivo(idUtente);
                                            if (isActive) {
                                                System.out.println("hai un abbonamento attivo.");
                                            } else {
                                                System.out.println("non hai un abbonamento attivo.");
                                            }
                                        } catch (IllegalArgumentException ex) {
                                            System.out.println("ID utente non valido: formato UUID non corretto.");
                                        } catch (NotFoundException ex) {
                                            System.out.println("Utente non trovato con l'ID specificato.");
                                        } catch (Exception ex) {
                                            System.out.println("Errore imprevisto: " + ex.getMessage());
                                        }
                                        break;
                                    case "12":
                                        while (true) {
                                            System.out.println("Gestisci Tratte");
                                            System.out.println("""
                                                    1. Cerca Tratta tramite id
                                                    2. Ottenere tutte le Tratte
                                                    3. Ottenere Tratte con una zona di partenza specifica
                                                    4. Ottenere Tratte che partono in un orario specifico
                                                    5. Ottenere Tratte associate a un determinato mezzo di trasporto
                                                    6. Ottenere Tratte tra una zona di partenza e un capolinea specifico
                                                    7. Ottenere tutte le Tratte ordinate per tempo di percorrenza previsto
                                                    8. Ottenere la Tratta con il tempo di percorrenza previsto più veloce
                                                    9. Cerca tratta tramite id
                                                    10. Trova tratta più veloce tramite una data
                                                    11. Trova il numero di giri in una determinata tratta
                                                    12. Trova la tratta del giro tramite id
                                                    13. Trova la tratta con il tempo di percorrenza previsto più veloce in base a una data
                                                    14. Trova lista biglietti per id tratta
                                                    15. Cerca giro tramite id
                                                    0. Torna al menu precedente""");
                                            String sceltaTratte = sc.nextLine();
                                            TrattaDAO trattaDAO = new TrattaDAO(em);

                                            switch (sceltaTratte) {
                                                case "1":
                                                    System.out.println("Inserisci l'ID della tratta:");
                                                    String idTratta = sc.nextLine();
                                                    Tratta trattaTrovata = trattaDAO.getByID(idTratta);
                                                    System.out.println(trattaTrovata);
                                                    break;
                                                case "2":
                                                    List<Tratta> tutteLeTratte = trattaDAO.getAllTratte();
                                                    tutteLeTratte.forEach(System.out::println);
                                                    break;
                                                case "3":
                                                    System.out.println("Inserisci la zona di partenza:");
                                                    String zonaPartenza = sc.nextLine();
                                                    List<Tratta> tratteZona = trattaDAO.getTratteByZonaPartenza(zonaPartenza);
                                                    tratteZona.forEach(System.out::println);
                                                    break;
                                                case "4":
                                                    System.out.println("Inserisci l'orario di partenza:");
                                                    double orarioPartenza = sc.nextDouble();
                                                    sc.nextLine();
                                                    List<Tratta> tratteOrario = trattaDAO.getTratteByOrarioPartenza(orarioPartenza);
                                                    tratteOrario.forEach(System.out::println);
                                                    break;
                                                case "5":
                                                    System.out.println("Inserisci l'ID del mezzo di trasporto:");
                                                    UUID mezzoId = UUID.fromString(sc.nextLine());
                                                    List<Tratta> tratteMezzo = trattaDAO.getTratteByMezzoId(mezzoId);
                                                    tratteMezzo.forEach(System.out::println);
                                                    break;
                                                case "6":
                                                    System.out.println("Inserisci la zona di partenza:");
                                                    String zonaPartenzaSpecifico = sc.nextLine();
                                                    System.out.println("Inserisci il capolinea:");
                                                    String capolinea = sc.nextLine();
                                                    List<Tratta> tratteSpecifiche = trattaDAO.getTratteByZonaPartenzaAndCapolinea(zonaPartenzaSpecifico, capolinea);
                                                    tratteSpecifiche.forEach(System.out::println);
                                                    break;
                                                case "7":
                                                    List<Tratta> tratteOrdinate = trattaDAO.getTratteOrderByTempoPercorrenza();
                                                    tratteOrdinate.forEach(System.out::println);
                                                    break;
                                                case "8":
                                                    Tratta trattaVeloce = trattaDAO.getTrattaConTempoPercorrenzaPiuVeloce();
                                                    System.out.println(trattaVeloce);
                                                    break;
                                                case "9":
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
                                                case "10":
                                                    try {
                                                        System.out.println("Inserisci una data");
                                                        LocalDate data = LocalDate.parse(sc.nextLine());
                                                        System.out.println(trd.findTrattaPiuVeloceFromData(data));
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "11":
                                                    try {
                                                        System.out.println("Inserisci l'id della tratta per trovare il numero di giri:");
                                                        String trattaId = sc.nextLine();
                                                        Long numeroGiri = md.trovaNumeroDiGiriInTratta(UUID.fromString(trattaId));
                                                        System.out.println("Numero di giri nella tratta con id " + trattaId + ": " + numeroGiri);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "12":
                                                    try {
                                                        System.out.println("Inserisci l'id del giro per trovare la tratta associata:");
                                                        String giroId = sc.nextLine();
                                                        Giro giro1 = gd.trovaTrattaPerGiro(UUID.fromString(giroId));
                                                        System.out.println("Tratta associata al giro con id " + giroId + ": " + giro1);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "13":
                                                    try {
                                                        System.out.println(trd.findTrattaConIlNumeroPiuAltoDiAbbonamenti());
                                                    } catch (Exception ex) {
                                                        System.out.println("Errore: " + ex.getMessage());
                                                    }
                                                    break;
                                                case "14":
                                                    try {
                                                        System.out.println("Inserisci id tratta");
                                                        String trattaID = sc.nextLine();
                                                        bd.trovaListaBigliettiPassatiInTratta(UUID.fromString(trattaID)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido: " + ex.getMessage());
                                                    }
                                                    break;
                                                case "15":
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
                                                case "0":
                                                    break;
                                                default:
                                                    System.out.println("Scelta non valida. Riprova.");
                                                    break;
                                            }
                                            if (sceltaTratte.equals("0")) break;
                                        }
                                        break;
                                    case "0":
                                        System.out.println("Torna al menu principale");
                                        break;
                                    default:
                                        System.out.println("Scelta non valida");
                                        break;
                                }
                                if (sceltaCrea.equals("0")) break;
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "0":
                        System.out.println("Uscita dal programma in corso...");
                        return;
                    default:
                        System.out.println("Scelta non valida riprova");
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
        System.out.println("1. Crea distributori automatici\n2. Crea rivenditori autorizzati");
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
                            statoDistributori = StatoDistributori.valueOf(sc.nextLine().toUpperCase());
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Inserisci un numero valido");
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
                            System.out.println("Inserisci un numero valido");
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
                System.out.println("Inserisci un numero valido");
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

    public static Tessera createTessera(UtenteDAO ud, PuntoDiEmissioneDAO ped, GiroDAO gd) {
        LocalDate initialDate;
        LocalDate endDate;
        boolean bool;
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
                System.out.println("Inserisci un numero valido");
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
            System.out.println("Inserisci id giro da abbinare alla tessera");
            String id2 = sc.nextLine();
            tessera = tesseraCreateOne(initialDate, endDate, bool, ud.getById(id), ped.getById(id1), gd.getByID(id2));
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
        TipoAbbonamento tipoAbbonamento;
        while (true) {
            try {
                System.out.println("Inserisci data ultimo rinnovo");
                date = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci stato abbonamento(ATTIVO,NON_ATTIVO)");
                statoAbbonamento = StatoAbbonamento.valueOf(sc.nextLine().toUpperCase());
                System.out.println("Inserisci tipo abbonamento (SETTIMANALE,MENSILE)");
                tipoAbbonamento = TipoAbbonamento.valueOf(sc.nextLine().toUpperCase());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("Inserisci id tessera ");
            String id = sc.nextLine();
            abbonamento = abbonamentoCreateOne(date, statoAbbonamento, tipoAbbonamento, td.getById(id));
            System.out.println("Abbonamento creato con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return abbonamento;
    }

    public static Biglietto createBiglietto(TesseraDAO td) {
        Biglietto biglietto = null;
        boolean vidimato;
        LocalDate date;
        while (true) {
            try {
                System.out.println("Inserisci se il biglietto è stato vidimato (true,false)");
                vidimato = Boolean.parseBoolean(sc.nextLine());
                System.out.println("Inserisci data di vidimazione");
                date = LocalDate.parse(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("Inserisci id tessera ");
            String id = sc.nextLine();
            biglietto = bigliettoCreateOne(vidimato, date, td.getById(id));
            System.out.println("Biglietto creato con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return biglietto;
    }

    public static Giro createGiro(TrattaDAO td, MezzoDAO md) {
        Giro giro = null;
        try {
            System.out.println("Inserisci id tratta ");
            String id = sc.nextLine();
            System.out.println("Inserisci id mezzo ");
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
                System.out.println("Inserisci un numero valido");
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
                tipoMezzo = TipoMezzo.valueOf(sc.nextLine().toUpperCase());
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
                System.out.println("Inserisci un numero valido");
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
                tipoMezzo = TipoMezzo.valueOf(sc.nextLine().toUpperCase());
                System.out.println("Inserisci data inizio manutenzione");
                dataInizio = LocalDate.parse(sc.nextLine());
                System.out.println("Inserisci data fine manutenzione");
                dataFine = LocalDate.parse(sc.nextLine());
                break;
            } catch (InputMismatchException e) {
                System.out.println("Inserisci un numero valido");
            } catch (Exception e) {
                System.out.println("Errore: " + e.getMessage());
            }
        }
        try {
            System.out.println("Inserisci id mezzo ");
            String id = sc.nextLine();
            manutenzione = manutenzioneCreateOne(tipoMezzo, dataInizio, dataFine, md.getById(id));
            System.out.println("Manutenzione creata con successo");
        } catch (Exception e) {
            System.out.println("Errore: " + e.getMessage());
        }
        return manutenzione;
    }
}
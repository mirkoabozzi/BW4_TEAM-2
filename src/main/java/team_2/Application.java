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

import java.time.LocalDate;
import java.util.*;

public class Application {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("bw4_team_2");
    static Scanner sc = new Scanner(System.in);
    static Random r = new Random();

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

    public static Tessera tesseraCreateOne(LocalDate initialDate, LocalDate endDate, boolean bool, Utente utente, PuntoDiEmissione puntoDiEmissione) {
        return new Tessera(initialDate, endDate, bool, utente, puntoDiEmissione);
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
            while (true) {
                System.out.println("1. Amministratore\n2. Utente\n3. Esci dal programma");
                String choice = sc.nextLine();
                switch (choice) {
                    case "1":
                        System.out.println("Inserisci password");
                        String pass = sc.nextLine();
                        if (pass.equals("1234")) {
                            System.out.println("1. Crea\n2. Elimina o modifica\n3. Cerca o filtra");
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
                                                    tessera = createTessera(ud, ped);
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
                                                    0. Torna al menu principale""");
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
                                    System.out.println("Cosa vuoi cercare o filtrare?");

                                    while (true) {
                                        try {
                                            System.out.println("""
                                                    1. Cerca punto di emissione tramite id\s
                                                    2. Cerca utente tramite id\s
                                                    3. Cerca tessera tramite id\s
                                                    4. Cerca mezzo tramite id\s
                                                    5. Cerca tratta tramite id\s
                                                    6. Cerca giro tramite id\s
                                                    7. Cerca manutenzione tramite id\s
                                                    8. Filtra abbonamenti per stato
                                                    9. Filtra abbonamenti per data di rinnovo
                                                    10. Filtra abbonamenti per tipo
                                                    11. Filtra abbonamenti tramite id tessera
                                                    12. Conta abbonamenti per tipo
                                                    13. Conta abbonamenti per stato
                                                    14. Filtra biglietti per stato
                                                    15. Filtra biglietti per data vidimazione
                                                    16. Trova biglietti tramite id tessera
                                                    17. Conta biglietti vidimati o non vidimati
                                                    18. Filtra mezzi per tipo
                                                    19. Conta mezzi in manutenzione
                                                    20. Conta mezzi in manutenzione per tipo
                                                    21. Verifica se la tessera è valida
                                                    22. Verifica abbonamenti in scadenza entro tot giorni
                                                    23. Trova tratta più veloce tramite una data
                                                    0. Torna al menu principale""");
                                            String sceltaCercaElimina = sc.nextLine();
                                            switch (sceltaCercaElimina) {
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
                                                        System.out.println("Quale utente vuoi cercare tramite id?");
                                                        String findId = sc.nextLine();
                                                        System.out.println(ud.getById(findId));
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Inserisci il formato corretto\n");
                                                    } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                    }
                                                    break;
                                                case "3":
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
                                                case "4":
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
                                                case "5":
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
                                                case "6":
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
                                                case "7":
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
                                                case "8":
                                                    try {
                                                        System.out.println("Vuoi filtrare gli abbonamenti ATTIVO o NON_ATTIVO?");
                                                        String statoAbbonamento = sc.nextLine().toUpperCase();
                                                        ad.filtraAbbonamentiPerStato(StatoAbbonamento.valueOf(statoAbbonamento)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "9":
                                                    try {
                                                        System.out.println("Inserisci la data per filtrare gli abbonamenti");
                                                        String data = sc.nextLine();
                                                        ad.filtraAbbonamentiRinnovatiInData(data).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "10":
                                                    try {
                                                        System.out.println("Inserisci il tipo di abbonamento (MENSILE o SETTIMANALE)");
                                                        String tipoAbbonamento = sc.nextLine().toUpperCase();
                                                        ad.filtraAbbonamentiPerTipo(TipoAbbonamento.valueOf(tipoAbbonamento)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "11":
                                                    try {
                                                        System.out.println("Inserisci un ID tessera valido");
                                                        String numeroTessera = sc.nextLine();
                                                        ad.trovaAbbonamentiTramiteTessera(UUID.fromString(numeroTessera)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "12":
                                                    try {
                                                        System.out.println("Inserisci il tipo SETTIMANALE o MENSILE");
                                                        String tipo = sc.nextLine().toUpperCase();
                                                        System.out.println("Abbonamenti di tipo " + tipo + " trovati: " + ad.contaAbbonamentiPerTipo(TipoAbbonamento.valueOf(tipo)));
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "13":
                                                    try {
                                                        System.out.println("Inserisci uno stato ATTIVO o NON_ATTIVO");
                                                        String stato = sc.nextLine().toUpperCase();
                                                        System.out.println("Abbonamenti " + stato + " trovati: " + ad.contaAbbonamentiPerStato(StatoAbbonamento.valueOf(stato)));
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "14":
                                                    try {
                                                        System.out.println("Inserisci true per i biglietti vidimati, false per i non vidimati");
                                                        Boolean stato = Boolean.valueOf(sc.nextLine());
                                                        bd.filtraBigliettiPerStato(stato).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "15":
                                                    try {
                                                        System.out.println("Inserisci una data valida");
                                                        String data = sc.nextLine();
                                                        bd.filtraBigliettiVidimatiInData(data).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "16":
                                                    try {
                                                        System.out.println("Inserisci un Id tessera valido");
                                                        String idTessera = sc.nextLine();
                                                        bd.trovaBigliettiTramiteIdTessera(UUID.fromString(idTessera)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "17":
                                                    try {
                                                        System.out.println("Quali biglietti vuoi contare? true vidimati / false non vidimati");
                                                        Boolean stato = Boolean.valueOf(sc.nextLine());
                                                        System.out.println("Biglietti trovati " + bd.contaBigliettiVidimati(stato));
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "18":
                                                    try {
                                                        System.out.println("Inserisci un tipo di mezzo valido AUTOBUS o TRAM");
                                                        String tipo = sc.nextLine().toUpperCase();
                                                        mnd.filtraMezziPerTipo(TipoMezzo.valueOf(tipo)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "19":
                                                    try {
                                                        System.out.println("Mezzi ancora in manutenzione");
                                                        mnd.mezziInManutenzione().forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "20":
                                                    try {
                                                        System.out.println("Inserisci un tipo mezzo valido AUTOBUS TRAM");
                                                        String tipo = sc.nextLine().toUpperCase();
                                                        System.out.println("Mezzi di tipo " + tipo + " presenti in officina " + mnd.contaMezziInManutenzionePerTipo(TipoMezzo.valueOf(tipo)));
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "21":
                                                    try {
                                                        System.out.println("Inserisci un id della tessera");
                                                        String idTessera = sc.nextLine();
                                                        td.validitàTessera(UUID.fromString(idTessera)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "22":
                                                    try {
                                                        System.out.println("Specifica il numero giorni");
                                                        String giorni = sc.nextLine();
                                                        ad.abbonamentiAttiviInScadenzaEntroGiorni(Integer.parseInt(giorni)).forEach(System.out::println);
                                                    } catch (Exception ex) {
                                                        System.out.println("Input non valido " + ex.getMessage());
                                                    }
                                                    break;
                                                case "23":
                                                    try {
                                                        System.out.println("Inserisci una data");
                                                        LocalDate data = LocalDate.parse(sc.nextLine());
                                                        trd.findTrattaPiuVeloceFromData(data);
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
                                            if (sceltaCercaElimina.equals("0")) break;
                                        } catch (Exception ex) {
                                            System.out.println(ex.getMessage());
                                        }
                                    }
                                }
                            }
                        }
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
                    case "3":
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

    public static Tessera createTessera(UtenteDAO ud, PuntoDiEmissioneDAO ped) {
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
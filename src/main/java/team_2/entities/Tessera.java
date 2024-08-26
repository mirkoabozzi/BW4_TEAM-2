package team_2.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessera")
public class Tessera {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    @Column(name = "validità_tessera")
    private boolean validitàTessera;

    @ManyToOne
    @JoinColumn(name = "punto_di_emissione_id", nullable = false)
    private PuntoDiEmissione puntoDiEmissione;

    @OneToOne
    @JoinColumn(name = "id-utente", nullable = false, unique = true)
    private Utente utente;

    @OneToMany(mappedBy = "tessera")
    private List<Abbonamento> listaAbbonamento;

    @OneToMany(mappedBy = "tessera")
    private List<Biglietto> listaBiglietto;

    @ManyToOne
    @JoinColumn(name = "giro_id", nullable = false)
    private Giro giroId;

    public Tessera() {
        //COSTRUTTORE DI DEFAULT
    }

    public Tessera(LocalDate dataInizio, LocalDate dataFine, boolean validitàTessera) {
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.validitàTessera = validitàTessera;
    }

    public UUID getId() {
        return id;
    }


    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public boolean isValiditàTessera() {
        return validitàTessera;
    }

    public void setValiditàTessera(boolean validitàTessera) {
        this.validitàTessera = validitàTessera;
    }

    public PuntoDiEmissione getPuntoDiEmissione() {
        return puntoDiEmissione;
    }

    public void setPuntoDiEmissione(PuntoDiEmissione puntoDiEmissione) {
        this.puntoDiEmissione = puntoDiEmissione;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public List<Abbonamento> getListaAbbonamento() {
        return listaAbbonamento;
    }

    public void setListaAbbonamento(List<Abbonamento> listaAbbonamento) {
        this.listaAbbonamento = listaAbbonamento;
    }

    public List<Biglietto> getListaBiglietto() {
        return listaBiglietto;
    }

    public void setListaBiglietto(List<Biglietto> listaBiglietto) {
        this.listaBiglietto = listaBiglietto;
    }
}

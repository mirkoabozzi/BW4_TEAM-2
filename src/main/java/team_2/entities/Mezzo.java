package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.TipoMezzo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzi")
public class Mezzo {

    //ATTRIBUTI

    @Enumerated(EnumType.STRING)
    protected TipoMezzo tipoMezzo;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "capienza")
    private int capienza;
    @Column(name = "lista_tratta")
    private int listaTratta;
    @Column(name = "in_servizio")
    private boolean inServizio;
    @Column(name = "data_inizio_servizio")
    private Date dataInizioServizio;
    @Column(name = "data_fine_servizio")
    private Date dataFineServizio;
    @Column(name = "numero_mezzo")
    private int numeroMezzo;
    @Column(name = "lista_manutenzione")
    private int listaManutenzione;

    @Column(name = "lista_giro")
    private long listaGiro;

    @OneToMany(mappedBy = "giro_id")
    private List<Giro> giroList;

    @ManyToMany(mappedBy = "mezzoList")
    private List<Tratta> trattaList;

    //COSTRUTTORI

    public Mezzo() {
        //COSTRUTTORE DI DEFAULT
    }

    public Mezzo(TipoMezzo tipoMezzo, int capienza, int listaTratta, boolean inServizio, Date dataInizioServizio, Date dataFineServizio, int numeroMezzo, int listaManutenzione, long listaGiro) {
        this.tipoMezzo = tipoMezzo;
        this.capienza = capienza;
        this.listaTratta = listaTratta;
        this.inServizio = inServizio;
        this.dataInizioServizio = dataInizioServizio;
        this.dataFineServizio = dataFineServizio;
        this.numeroMezzo = numeroMezzo;
        this.listaManutenzione = listaManutenzione;
        this.listaGiro = listaGiro;
    }
    //GETTER SETTER

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public int getListaTratta() {
        return listaTratta;
    }

    public void setListaTratta(int listaTratta) {
        this.listaTratta = listaTratta;
    }

    public boolean isInServizio() {
        return inServizio;
    }

    public void setInServizio(boolean inServizio) {
        this.inServizio = inServizio;
    }

    public Date getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(Date dataInizioServizio) {
        this.dataInizioServizio = dataInizioServizio;
    }

    public Date getDataFineServizio() {
        return dataFineServizio;
    }

    public void setDataFineServizio(Date dataFineServizio) {
        this.dataFineServizio = dataFineServizio;
    }

    public int getNumeroMezzo() {
        return numeroMezzo;
    }

    public void setNumeroMezzo(int numeroMezzo) {
        this.numeroMezzo = numeroMezzo;
    }

    public int getListaManutenzione() {
        return listaManutenzione;
    }

    public void setListaManutenzione(int listaManutenzione) {
        this.listaManutenzione = listaManutenzione;
    }

    public long getListaGiro() {
        return listaGiro;
    }

    public void setListaGiro(long listaGiro) {
        this.listaGiro = listaGiro;
    }

    public List<Giro> getGiroList() {
        return giroList;
    }

    public void setGiroList(List<Giro> giroList) {
        this.giroList = giroList;
    }

    public List<Tratta> getTrattaList() {
        return trattaList;
    }

    public void setTrattaList(List<Tratta> trattaList) {
        this.trattaList = trattaList;
    }

    public UUID getId() {
        return id;
    }


    //TO STRING


    @Override
    public String toString() {
        return "Mezzo{" +
                "tipoMezzo=" + tipoMezzo +
                ", id=" + id +
                ", capienza=" + capienza +
                ", listaTratta=" + listaTratta +
                ", inServizio=" + inServizio +
                ", dataInizioServizio=" + dataInizioServizio +
                ", dataFineServizio=" + dataFineServizio +
                ", numeroMezzo=" + numeroMezzo +
                ", listaManutenzione=" + listaManutenzione +
                ", listaGiro=" + listaGiro +
                ", giroList=" + giroList +
                ", trattaList=" + trattaList +
                '}';
    }
}

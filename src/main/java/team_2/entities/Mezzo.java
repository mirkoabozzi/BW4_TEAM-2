package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.TipoMezzo;

import java.time.LocalDate;
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
    @Column(name = "in_servizio")
    private boolean inServizio;
    @Column(name = "data_inizio_servizio")
    private LocalDate dataInizioServizio;
    @Column(name = "data_fine_servizio")
    private LocalDate dataFineServizio;
    @Column(name = "numero_mezzo")
    private int numeroMezzo;

    @OneToMany(mappedBy = "mezzo")
    private List<Giro> giroList;

    @OneToMany(mappedBy = "mezzoList")
    private List<Tratta> trattaList;

    @OneToMany(mappedBy = "mezzo")
    private List<Manutenzione> manutenzioneList;

    //COSTRUTTORI

    public Mezzo() {
        //COSTRUTTORE DI DEFAULT
    }

    public Mezzo(TipoMezzo tipoMezzo, int capienza, boolean inServizio, LocalDate dataInizioServizio, LocalDate dataFineServizio, int numeroMezzo, List<Giro> giroList, List<Tratta> trattaList, List<Manutenzione> manutenzioneList) {
        this.tipoMezzo = tipoMezzo;
        this.capienza = capienza;
        this.inServizio = inServizio;
        this.dataInizioServizio = dataInizioServizio;
        this.dataFineServizio = dataFineServizio;
        this.numeroMezzo = numeroMezzo;
        this.giroList = giroList;
        this.trattaList = trattaList;
        this.manutenzioneList = manutenzioneList;
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


    public boolean isInServizio() {
        return inServizio;
    }

    public void setInServizio(boolean inServizio) {
        this.inServizio = inServizio;
    }

    public LocalDate getDataInizioServizio() {
        return dataInizioServizio;
    }

    public void setDataInizioServizio(LocalDate dataInizioServizio) {
        this.dataInizioServizio = dataInizioServizio;
    }

    public LocalDate getDataFineServizio() {
        return dataFineServizio;
    }

    public void setDataFineServizio(LocalDate dataFineServizio) {
        this.dataFineServizio = dataFineServizio;
    }

    public int getNumeroMezzo() {
        return numeroMezzo;
    }

    public void setNumeroMezzo(int numeroMezzo) {
        this.numeroMezzo = numeroMezzo;
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
                ", inServizio=" + inServizio +
                ", dataInizioServizio=" + dataInizioServizio +
                ", dataFineServizio=" + dataFineServizio +
                ", numeroMezzo=" + numeroMezzo +
                ", giroList=" + giroList +
                ", trattaList=" + trattaList +
                '}';
    }
}

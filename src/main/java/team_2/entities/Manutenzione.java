package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.TipoMezzo;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "manutenzione")
public class Manutenzione {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "tipo_mezzo")
    private TipoMezzo tipoMezzo;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;

    @ManyToOne
    private Mezzo mezzo;

    public Manutenzione() {
    }

    public Manutenzione(TipoMezzo tipoMezzo, LocalDate dataInizio, LocalDate dataFine, Mezzo mezzo) {
        this.tipoMezzo = tipoMezzo;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.mezzo = mezzo;
    }

    public UUID getId() {
        return id;
    }

    public TipoMezzo getTipoMezzo() {
        return tipoMezzo;
    }

    public void setTipoMezzo(TipoMezzo tipoMezzo) {
        this.tipoMezzo = tipoMezzo;
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

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }

    @Override
    public String toString() {
        return "Manutenzione{" +
                "id=" + id +
                ", tipoMezzo=" + tipoMezzo +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", mezzo=" + mezzo +
                '}';
    }
}


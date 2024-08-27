package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.StatoAbbonamento;
import team_2.enums.TipoAbbonamento;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "abbonamento")
public class Abbonamento {
    //attributi
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "data_ultimo_rinnovo")
    private LocalDate dataUltimoRinnovo;
    @Enumerated(EnumType.STRING)
    private StatoAbbonamento statoAbbonamento;
    @Enumerated(EnumType.STRING)
    private TipoAbbonamento tipoAbbonamento;

    @ManyToOne
    @JoinColumn(name = "tessera_id")
    private Tessera tessera;

    //costruttori
    public Abbonamento() {
    }

    public Abbonamento(LocalDate dataUltimoRinnovo, StatoAbbonamento statoAbbonamento, TipoAbbonamento tipoAbbonamento, Tessera tessera) {
        this.dataUltimoRinnovo = dataUltimoRinnovo;
        this.statoAbbonamento = statoAbbonamento;
        this.tipoAbbonamento = tipoAbbonamento;
        this.tessera = tessera;
    }

    //getter e setter

    public UUID getId() {
        return id;
    }

    public LocalDate getDataUltimoRinnovo() {
        return dataUltimoRinnovo;
    }

    public void setDataUltimoRinnovo(LocalDate dataUltimoRinnovo) {
        this.dataUltimoRinnovo = dataUltimoRinnovo;
    }

    public StatoAbbonamento getStatoAbbonamento() {
        return statoAbbonamento;
    }

    public void setStatoAbbonamento(StatoAbbonamento statoAbbonamento) {
        this.statoAbbonamento = statoAbbonamento;
    }

    public TipoAbbonamento getTipo() {
        return tipoAbbonamento;
    }

    public void setTipo(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

    public Tessera getTessera() {
        return tessera;
    }

    public void setTessera(Tessera tessera) {
        this.tessera = tessera;
    }

//to string

    @Override
    public String toString() {
        return "Abbonamento{" +
                "id=" + id +
                ", dataUltimoRinnovo=" + dataUltimoRinnovo +
                ", statoAbbonamento=" + statoAbbonamento +
                ", tipo=" + tipoAbbonamento +
                '}';
    }
}

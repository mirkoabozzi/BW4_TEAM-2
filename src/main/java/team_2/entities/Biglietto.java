package team_2.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "biglietto")
public class Biglietto {
    //attributi
    @Id
    @GeneratedValue
    private UUID id;
    private boolean vidimizzato;
    @Column(name = "data_vidimizzazione")
    private LocalDate dataVidimizzazione;

    @ManyToOne
    @JoinColumn(name = "tessera_id", nullable = false)
    private Tessera tessera;

    //costruttori
    public Biglietto() {

    }

    public Biglietto(boolean vidimizzato, LocalDate dataVidimizzazione, Tessera tessera) {
        this.vidimizzato = vidimizzato;
        this.dataVidimizzazione = dataVidimizzazione;
        this.tessera = tessera;
    }

    //getter e setter
    public UUID getId() {
        return id;
    }

    public boolean isVidimizzato() {
        return vidimizzato;
    }

    public void setVidimizzato(boolean vidimizzato) {
        this.vidimizzato = vidimizzato;
    }

    public LocalDate getDataVidimizzazione() {
        return dataVidimizzazione;
    }

    public void setDataVidimizzazione(LocalDate dataVidimizzazione) {
        this.dataVidimizzazione = dataVidimizzazione;
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
        return "Biglietto{" +
                "id=" + id +
                ", vidimizzato=" + vidimizzato +
                ", dataVidimizzazione=" + dataVidimizzazione +
                //", tessera=" + tessera +
                '}';
    }
}

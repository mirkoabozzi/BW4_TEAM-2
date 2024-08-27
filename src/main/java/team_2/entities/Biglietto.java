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
    private boolean vidimato;
    @Column(name = "data_vidimazione")
    private LocalDate dataVidimazione;

    @ManyToOne
    @JoinColumn(name = "tessera_id", nullable = false)
    private Tessera tessera;

    //costruttori
    public Biglietto() {

    }

    public Biglietto(boolean vidimato, LocalDate dataVidimazione, Tessera tessera) {
        this.vidimato = vidimato;
        this.dataVidimazione = dataVidimazione;
        this.tessera = tessera;
    }

    //getter e setter
    public UUID getId() {
        return id;
    }

    public boolean isVidimato() {
        return vidimato;
    }

    public void setVidimato(boolean vidimato) {
        this.vidimato = vidimato;
    }

    public LocalDate getDataVidimazione() {
        return dataVidimazione;
    }

    public void setDataVidimazione(LocalDate dataVidimazione) {
        this.dataVidimazione = dataVidimazione;
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
                ", vidimato=" + vidimato +
                ", dataVidimazione=" + dataVidimazione +
                //", tessera=" + tessera +
                '}';
    }
}

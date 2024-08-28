package team_2.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tratta")
public class Tratta {

    //ATTRIBUTI

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "zona_partenza")
    private String zanaPartenza;

    @Column(name = "capolinea")
    private String capolinea;

    @Column(name = "tempo_percorrenza_previsto")
    private double tempoPercorrenzaPrevisto;

    @Column(name = "tempo_percorrenza_effettivo")
    private double tempoPercorrenzaEffettivo;

    @Column(name = "orario_partenza")
    private double orarioPartenza;

    @OneToMany(mappedBy = "tratta")
    private List<Giro> giroList;

    @ManyToMany
    @JoinTable(
            name = "join_tratta_mezzo",
            joinColumns = @JoinColumn(name = "tratta_id"),
            inverseJoinColumns = @JoinColumn(name = "mezzo_id")
    )
    private List<Mezzo> mezzoList;

    //COSTRUTTORI


    public Tratta() {

    }

    public Tratta(String zanaPartenza, String capolinea, double tempoPercorrenzaPrevisto, double tempoPercorrenzaEffettivo, double orarioPartenza, List<Mezzo> mezzoList) {
        this.zanaPartenza = zanaPartenza;
        this.capolinea = capolinea;
        this.tempoPercorrenzaPrevisto = tempoPercorrenzaPrevisto;
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
        this.orarioPartenza = orarioPartenza;
        this.mezzoList = mezzoList;
    }


    //SETTER E GETTER

    public UUID getId() {
        return id;
    }

    public String getZanaPartenza() {
        return zanaPartenza;
    }

    public void setZanaPartenza(String zanaPartenza) {
        this.zanaPartenza = zanaPartenza;
    }

    public String getCapolinea() {
        return capolinea;
    }

    public void setCapolinea(String capolinea) {
        this.capolinea = capolinea;
    }

    public double getTempoPercorrenzaPrevisto() {
        return tempoPercorrenzaPrevisto;
    }

    public void setTempoPercorrenzaPrevisto(double tempoPercorrenzaPrevisto) {
        this.tempoPercorrenzaPrevisto = tempoPercorrenzaPrevisto;
    }

    public double getTempoPercorrenzaEffettivo() {
        return tempoPercorrenzaEffettivo;
    }

    public void setTempoPercorrenzaEffettivo(double tempoPercorrenzaEffettivo) {
        this.tempoPercorrenzaEffettivo = tempoPercorrenzaEffettivo;
    }

    public double getOrarioPartenza() {
        return orarioPartenza;
    }

    public void setOrarioPartenza(double orarioPartenza) {
        this.orarioPartenza = orarioPartenza;
    }

    public List<Giro> getGiroList() {
        return giroList;
    }

    public void setGiroList(List<Giro> giroList) {
        this.giroList = giroList;
    }

    public List<Mezzo> getMezzoList() {
        return mezzoList;
    }

    public void setMezzoList(List<Mezzo> mezzoList) {
        this.mezzoList = mezzoList;
    }


    //TO STRING


    @Override
    public String toString() {
        return "Tratta{" +
                "id=" + id +
                ", zanaPartenza='" + zanaPartenza + '\'' +
                ", capolinea='" + capolinea + '\'' +
                ", tempoPercorrenzaPrevisto=" + tempoPercorrenzaPrevisto +
                ", tempoPercorrenzaEffettivo=" + tempoPercorrenzaEffettivo +
                ", orarioPartenza=" + orarioPartenza +
                // ", giroList=" + giroList +
                //", mezzoList=" + mezzoList +
                '}';
    }
}

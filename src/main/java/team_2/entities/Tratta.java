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

    @Column(name = "tempo_percorrenza")
    private double tempoPercorrenza;

    @Column(name = "lista_mezzi")
    private int listaMezzi;

    @Column(name = "lista_numero_giri")
    private int listaNumeroGiri;

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

    public Tratta(String zanaPartenza, String capolinea, double tempoPercorrenza, int listaMezzi, int listaNumeroGiri, double orarioPartenza, List<Giro> giroList, List<Mezzo> mezzoList) {
        this.zanaPartenza = zanaPartenza;
        this.capolinea = capolinea;
        this.tempoPercorrenza = tempoPercorrenza;
        this.listaMezzi = listaMezzi;
        this.listaNumeroGiri = listaNumeroGiri;
        this.orarioPartenza = orarioPartenza;
        this.giroList = giroList;
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

    public double getTempoPercorrenza() {
        return tempoPercorrenza;
    }

    public void setTempoPercorrenza(double tempoPercorrenza) {
        this.tempoPercorrenza = tempoPercorrenza;
    }

    public int getListaMezzi() {
        return listaMezzi;
    }

    public void setListaMezzi(int listaMezzi) {
        this.listaMezzi = listaMezzi;
    }

    public int getListaNumeroGiri() {
        return listaNumeroGiri;
    }

    public void setListaNumeroGiri(int listaNumeroGiri) {
        this.listaNumeroGiri = listaNumeroGiri;
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
                ", tempoPercorrenza=" + tempoPercorrenza +
                ", listaMezzi=" + listaMezzi +
                ", listaNumeroGiri=" + listaNumeroGiri +
                ", orarioPartenza=" + orarioPartenza +
                ", giroList=" + giroList +
                ", mezzoList=" + mezzoList +
                '}';
    }
}

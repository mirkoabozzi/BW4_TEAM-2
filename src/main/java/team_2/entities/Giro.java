package team_2.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "giro")
public class Giro {
    //ATTRIBUTI

    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "giroId")
    private List<Tessera> tesseraList;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private Tratta tratta;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private Mezzo mezzo;


    //COSTRUTTORI

    public Giro() {
        //COSTRUTTORE DEFAULT
    }

    public Giro(List<Tessera> tesseraList, Tratta tratta, Mezzo mezzo) {
        this.tesseraList = tesseraList;
        this.tratta = tratta;
        this.mezzo = mezzo;
    }

    //SETTER E GETTER

    public UUID getId() {
        return id;
    }


    public List<Tessera> getTesseraList() {
        return tesseraList;
    }

    public void setTesseraList(List<Tessera> tesseraList) {
        this.tesseraList = tesseraList;
    }

    public Tratta getTratta() {
        return tratta;
    }

    public void setTratta(Tratta tratta) {
        this.tratta = tratta;
    }

    public Mezzo getMezzo() {
        return mezzo;
    }

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }


    //TO STRING


    @Override
    public String toString() {
        return "Giro{" + "id=" + id + ", tesseraList=" + tesseraList + ", tratta=" + tratta + ", mezzo=" + mezzo + '}';
    }
}

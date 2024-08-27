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

    public Giro(Tratta tratta, Mezzo mezzo) {
        this.tratta = tratta;
        this.mezzo = mezzo;
    }

    //SETTER E GETTER

    public Giro(List<Tessera> tesseraList, UUID id) {
        this.tesseraList = tesseraList;
        this.id = id;
    }

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


    //TO STRING

    public void setMezzo(Mezzo mezzo) {
        this.mezzo = mezzo;
    }
}

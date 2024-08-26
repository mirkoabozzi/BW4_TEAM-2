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

    @Column(name = "tratta_id")
    private UUID trattaId;

    @Column(name = "lista_tessere")
    private UUID listaTessere;

    @Column(name = "mezzo_id")
    private UUID mezzoId;

    @OneToMany(mappedBy = "tessera")
    private List<Tessera> tesseraList;

    @ManyToOne
    @JoinColumn(name = "tratta_id")
    private List<Tratta> trattaList;

    @ManyToOne
    @JoinColumn(name = "mezzo_id")
    private List<Mezzo> mezzoList;

    //COSTRUTTORI

    public Giro() {
        //COSTRUTTORE DEFAULT
    }

    public Giro(UUID trattaId, UUID listaTessere, UUID mezzoId) {
        this.trattaId = trattaId;
        this.listaTessere = listaTessere;
        this.mezzoId = mezzoId;
    }


    //SETTER E GETTER

    public UUID getId() {
        return id;
    }

    public UUID getTrattaId() {
        return trattaId;
    }

    public void setTrattaId(UUID trattaId) {
        this.trattaId = trattaId;
    }

    public UUID getListaTessere() {
        return listaTessere;
    }

    public void setListaTessere(UUID listaTessere) {
        this.listaTessere = listaTessere;
    }

    public UUID getMezzoId() {
        return mezzoId;
    }

    public void setMezzoId(UUID mezzoId) {
        this.mezzoId = mezzoId;
    }


    //TO STRING


    @Override
    public String toString() {
        return "Giro{" +
                "id=" + id +
                ", trattaId=" + trattaId +
                ", listaTessere=" + listaTessere +
                ", mezzoId=" + mezzoId +
                '}';
    }
}

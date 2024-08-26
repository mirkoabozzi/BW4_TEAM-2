package team_2.entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "punto_di_emissione")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_punto_emissione")
public abstract class PuntoDiEmissione {
    @OneToMany(mappedBy = "puntoDiEmissione")
    List<Tessera> listaTessera;
    //attributi
    @Id
    @GeneratedValue
    private UUID id;
    //costruttori

    public PuntoDiEmissione() {

    }

    //getter e setter

    public List<Tessera> getListaTessera() {
        return listaTessera;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    //to string

    @Override
    public String toString() {
        return "PuntoDiEmissione{" +
                //"listaTessera=" + listaTessera +
                ", id=" + id +
                '}';
    }
}

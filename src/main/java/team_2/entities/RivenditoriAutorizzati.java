package team_2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "rivenditori_autorizzati")
public class RivenditoriAutorizzati extends PuntoDiEmissione {
    //attributi
    private String name;
    //costruttori

    public RivenditoriAutorizzati() {

    }

    public RivenditoriAutorizzati(String name) {
        this.name = name;
    }
    //getter e setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    //to String
    @Override
    public String toString() {
        return "RivenditoriAutorizzati{" +
                "name='" + name + '\'' +
                '}';
    }
}

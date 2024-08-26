package team_2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import team_2.enums.Stato;

@Entity
@Table(name = "distributori_automatici")
public class DistributoriAutomatici extends PuntoDiEmissione {
    //attributi
    private Stato stato;

    //costruttori
    public DistributoriAutomatici() {

    }

    public DistributoriAutomatici(Stato stato) {
        this.stato = stato;
    }

    //getter e setter
    public Stato getStato() {
        return stato;
    }

    public void setStato(Stato stato) {
        this.stato = stato;
    }

    //to String
    @Override
    public String toString() {
        return "DistributoriAutomatici{" +
                "stato=" + stato +
                '}';
    }
}

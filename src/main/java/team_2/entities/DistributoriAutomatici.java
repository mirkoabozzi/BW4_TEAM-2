package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.StatoDistributori;

@Entity
@Table(name = "distributori_automatici")
public class DistributoriAutomatici extends PuntoDiEmissione {
    //attributi
    @Column(name = "stato_distributori")
    @Enumerated(EnumType.STRING)
    private StatoDistributori statoDistributori;

    //costruttori
    public DistributoriAutomatici() {

    }

    public DistributoriAutomatici(StatoDistributori statoDistributori) {
        this.statoDistributori = statoDistributori;
    }

    //getter e setter
    public StatoDistributori getStato() {
        return statoDistributori;
    }

    public void setStato(StatoDistributori statoDistributori) {
        this.statoDistributori = statoDistributori;
    }

    //to String
    @Override
    public String toString() {
        return "DistributoriAutomatici{" +
                "stato=" + statoDistributori +
                '}';
    }
}

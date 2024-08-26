package team_2.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tessera")
public class Tessera {
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "data_inizio")
    private LocalDate dataInizio;
    @Column(name = "data_fine")
    private LocalDate dataFine;
    @Column(name = "validità_tessera")
    private boolean validitàTessera;

    @ManyToOne
    @JoinColumn(name = "punto_di_emissione_id", nullable = false)
    private PuntoDiEmissione puntoDiEmissione;

    @OneToOne
    @JoinColumn(name = "id-utente", nullable = false, unique = true)
    private Utente utente;

    @OneToMany(mappedBy = "tessera")
    private List<Abbonamento> listaAbbonamento;

    @OneToMany(mappedBy = "tessera")
    private List<Biglietto> listaBiglietto;

    //manca la lista giri!!

}

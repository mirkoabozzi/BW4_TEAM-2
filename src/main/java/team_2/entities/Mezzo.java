package team_2.entities;

import jakarta.persistence.*;
import team_2.enums.TipoMezzo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "mezzi")
public class Mezzo {
    @Enumerated(EnumType.STRING)
    protected TipoMezzo tipoMezzo;
    @Id
    @GeneratedValue
    private UUID id;
    @Column(name = "capienza")
    private int capienza;
    @Column(name = "lista_tratta")
    private int listaTratta;
    @Column(name = "in_servizio")
    private boolean inServizio;
    @Column(name = "data_inizio_servizo")
    private Date dataInizioServizio;
    @Column(name = "data_fine_servizio")
    private Date dataFineServizio;
    @Column(name = "numero_mezzo")
    private int numeroMezzo;
    @Column(name = "lista_manuntenzione")
    private int listaManuntenzione;

    @Column(name = "lista_giro")
    private long listaGiro;

    @OneToMany(mappedBy = "giro_id")
    private List<Giro> giroList;

    //LISTA
}

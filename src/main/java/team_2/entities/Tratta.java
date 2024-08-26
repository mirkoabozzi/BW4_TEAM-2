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

    @OneToMany(mappedBy = "giro_id")
    private List<Giro> giroList;

    //JUMPTION TABLE

    //COSTRUTTORI

    //SETTER E GETTER

    //TO STRING


}

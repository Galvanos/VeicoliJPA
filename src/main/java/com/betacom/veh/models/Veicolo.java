package com.betacom.veh.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "veicolo")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter @SuperBuilder @NoArgsConstructor @AllArgsConstructor
public class Veicolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "tipo_veicolo", nullable = false, length = 50)
    private String tipoVeicolo;

    @Column(name = "numero_ruote", nullable = false)
    private Integer numeroRuote;

    @Column(name = "alimentazione", nullable = false, length = 30)
    private String tipoAlimentazione;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "colore", nullable = false, length = 30)
    private String colore;

    @Column(name = "marca", nullable = false, length = 50)
    private String marca;

    @Column(name = "anno_produzione", nullable = false)
    private Integer annoProduzione;

    @Column(name = "modello", nullable = false, length = 50)
    private String modello;
}
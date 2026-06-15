package com.betacom.veh.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Getter 
@Setter 
@SuperBuilder 
@NoArgsConstructor 
@AllArgsConstructor
@Entity
@Table(name = "bici")
public class Bici extends Veicolo {
	@Column(name = "numero_marce")
    private Integer numeroMarce;

    @Column(name = "tipo_freno", length = 30)
    private String tipoFreno;

    @Column(name = "tipo_sospensione", length = 30)
    private String tipoSospensione;

    @Column(name = "is_pieghevole")
    private Boolean pieghevole;
}
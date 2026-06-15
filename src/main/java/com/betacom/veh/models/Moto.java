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
@Table(name = "moto")
public class Moto extends Veicolo {
	@Column(name = "targa", nullable = false, unique = true, length = 10)
    private String targa;

    @Column(name = "cilindrata")
    private Integer cc;
}
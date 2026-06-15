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
@Table(name = "automobile")
public class Automobile extends Veicolo {
	@Column(name = "targa", nullable = false, unique = true, length = 10)
    private String targa;

    @Column(name = "numero_porte")
    private Integer numeroPorte;

    @Column(name = "cilindrata")
    private Integer cc;
}
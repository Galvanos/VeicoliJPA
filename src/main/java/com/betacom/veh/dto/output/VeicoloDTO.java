package com.betacom.veh.dto.output;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

/**
 * Classe DTO del veicolo per contenere i dati in comune da cui derivare gli altri DTO
 * per i veicoli
 */
@Getter
@Setter
@SuperBuilder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class VeicoloDTO {

	 
	    private Integer id;	   
	    private String tipoVeicolo;	   
	    private Integer numeroRuote;	   
	    private String tipoAlimentazione;
	    private String categoria;
	    private String colore;
	    private String marca;
	    private Integer annoProduzione;
	    private String modello;
}

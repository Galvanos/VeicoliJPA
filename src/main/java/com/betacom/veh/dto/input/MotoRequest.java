package com.betacom.veh.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MotoRequest {
	private String targa;
	private Integer cc;
	
	private Integer id;
    private Integer numeroRuote;
    private String tipoAlimentazione;
    private String categoria;
    private String colore;
    private String marca;
    private Integer annoProduzione;
    private String modello;

}

package com.betacom.veh.dto.input;

import java.util.List;

import com.betacom.veh.dto.validation.ValidationGroups;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class ListVeicoloRequest {

	private List<Integer> id;
    private List<Integer> numeroRuote;
    private List<String> tipoAlimentazione;
    private List<String> categoria;
    private List<String> colore;
    private List<String> marca;
    private List<Integer> annoProduzione;
    private List<String> modello;
 	private List<Integer> numeroRapporti;
    private List<String> tipoFreno;
    private List<String> tipoSospensione;
	private List<Boolean> pieghevole;
 	private List<String> targa;
    private List<Integer> numeroPorte;
    private List<Integer> cc;
}

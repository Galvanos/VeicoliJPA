package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.models.Automobile;

public class AutomobileMap {
	
	public static AutomobileDTO buildAutomobileDTO(Automobile automobile) {
		
		return AutomobileDTO.builder()
				.annoProduzione(automobile.getAnnoProduzione())
				.categoria(automobile.getCategoria())
				.cc(automobile.getCc())
				.colore(automobile.getColore())
				.id(automobile.getId())
				.marca(automobile.getMarca())
				.modello(automobile.getModello())
				.numeroPorte(automobile.getNumeroPorte())
				.numeroRuote(automobile.getNumeroRuote())
				.targa(automobile.getTarga())
				.tipoAlimentazione(automobile.getTipoAlimentazione())
				.tipoVeicolo("AUTOMOBILE")
				.build();
		
	}
	
	public static List<AutomobileDTO> buildAutomobileDTOList(List<Automobile> list){
		return list.stream().map(AutomobileMap::buildAutomobileDTO).toList();
	}

}

package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.models.Bici;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BiciMap {
	
	public static BiciDTO buildBiciDTO(Bici bici) {
		
		return BiciDTO.builder()
				.id(null)
				.tipoVeicolo("BICICLETTA")
				.numeroRuote(bici.getNumeroRuote())
				.tipoAlimentazione(bici.getTipoAlimentazione())
				.categoria(bici.getCategoria())
				.colore(bici.getColore())
				.marca(bici.getMarca())
				.annoProduzione(bici.getAnnoProduzione())
				.modello(bici.getModello())
				.numeroRapporti(bici.getNumeroRapporti())
				.tipoFreno(bici.getTipoFreno())
				.tipoSospensione(bici.getTipoSospensione())
				.pieghevole(bici.getPieghevole())
				.build();
	}
	
	public static List<BiciDTO> buildBiciDTOList(List<Bici> list){
		return list.stream().map(BiciMap::buildBiciDTO).toList();
	}
}

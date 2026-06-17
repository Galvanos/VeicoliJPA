package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.models.Bici;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BiciMap {
	
	public static BiciDTO buildBiciDTO(Bici bici) {
		
		return BiciDTO.builder()
				.numeroRapporti(bici.getNumeroRapporti())
				.tipoFreno(bici.getTipoFreno())
				.tipoSospensione(bici.getTipoSospensione())
				.pieghevole(bici.getPieghevole())
				.build();
	}
	
	public static List<BiciDTO> buildAutomobileDTOList(List<Bici> list){
		return list.stream().map(BiciMap::buildBiciDTO).toList();
	}
}

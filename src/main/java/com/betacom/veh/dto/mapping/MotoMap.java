package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.MotoDTO;
import com.betacom.veh.models.Moto;

public class MotoMap {
public static List<MotoDTO> buildMotoDTOList(List<Moto> lMoto){
		
		return lMoto.stream()
				.map(moto -> buildMotoDTO(moto)
						).toList();			

	}

	public static MotoDTO buildMotoDTO(Moto moto) {
		return MotoDTO.builder()
				.id(moto.getId())
	            .tipoVeicolo(moto.getTipoVeicolo())
	            .numeroRuote(moto.getNumeroRuote())
	            .tipoAlimentazione(moto.getTipoAlimentazione())
	            .categoria(moto.getCategoria())
	            .colore(moto.getColore())
	            .marca(moto.getMarca())
	            .annoProduzione(moto.getAnnoProduzione())
	            .modello(moto.getModello())
	            
				.targa(moto.getTarga())
				.cc(moto.getCc())
				.build();
	}

}

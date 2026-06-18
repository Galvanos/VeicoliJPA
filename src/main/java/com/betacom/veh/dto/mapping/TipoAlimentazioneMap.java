package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.TipoAlimentazioneDTO;
import com.betacom.veh.models.TipoAlimentazione;

public class TipoAlimentazioneMap {

	
	public static TipoAlimentazioneDTO buildTipoAlimentazioneDTO(TipoAlimentazione tipoAlimentazione) {
		TipoAlimentazioneDTO tipoAlimentazioneDTO = new TipoAlimentazioneDTO();
		tipoAlimentazioneDTO.setTipoVeicolo(tipoAlimentazione.getTipoAlimentazioneId().getTipoVeicolo());
		tipoAlimentazioneDTO.setTipoAlimentazione(tipoAlimentazione.getTipoAlimentazioneId().getTipoAlimentazione());
		return tipoAlimentazioneDTO;
	}
	
	public static List<TipoAlimentazioneDTO> buildTipoAlimentazioneDTOList(List<TipoAlimentazione> list){
		 return list.stream().map(TipoAlimentazioneMap::buildTipoAlimentazioneDTO).toList();
	}
}

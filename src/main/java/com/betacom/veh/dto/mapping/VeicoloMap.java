package com.betacom.veh.dto.mapping;

import java.util.List;

import com.betacom.veh.dto.output.VeicoloDTO;
import com.betacom.veh.models.Automobile;
import com.betacom.veh.models.Bici;
import com.betacom.veh.models.Moto;
import com.betacom.veh.models.Veicolo;

public class VeicoloMap {

	 public static VeicoloDTO buildVeicoloDTO(Veicolo veicolo) {

	        if (veicolo instanceof Automobile automobile) {
	            return AutomobileMap.buildAutomobileDTO(automobile);
	        }

	        if (veicolo instanceof Moto moto) {
	            return MotoMap.buildMotoDTO(moto);
	        }

	        if (veicolo instanceof Bici bici) {
	            return BiciMap.buildBiciDTO(bici); 
	        }

	        throw new IllegalArgumentException("Tipo di veicolo non supportato " + veicolo.getClass().getSimpleName());
	    }
	 
	 public static List<VeicoloDTO> buildVeicoloDTOList(List<Veicolo> list) {

		    return list.stream()
		            .map(VeicoloMap::buildVeicoloDTO)
		            .toList();
		}
}

package com.betacom.veh.services.implementation;

import com.betacom.veh.repositories.IVeicoloRepository;
import org.springframework.stereotype.Service;

import com.betacom.veh.services.interfaces.IVeicoloService;
import java.util.List;

import com.betacom.veh.dto.mapping.VeicoloMap;
import com.betacom.veh.dto.output.VeicoloDTO;
import com.betacom.veh.models.Veicolo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeicoloImplementation implements IVeicoloService{

	private final IVeicoloRepository veicoloRepository;

	
	@Override
	public List<VeicoloDTO> findAll() {
		return VeicoloMap.buildVeicoloDTOList(
	            veicoloRepository.findAll());
	}

	@Override
	public VeicoloDTO findById(Integer id) {
		 Veicolo veicolo = veicoloRepository.findById(id)
		            .orElseThrow(() -> new RuntimeException("Veicolo non trovato"));

		    return VeicoloMap.buildVeicoloDTO(veicolo);
	}

}

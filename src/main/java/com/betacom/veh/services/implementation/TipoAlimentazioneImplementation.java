package com.betacom.veh.services.implementation;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.veh.dto.input.TipoAlimentazioneRequest;
import com.betacom.veh.dto.mapping.TipoAlimentazioneMap;
import com.betacom.veh.dto.output.TipoAlimentazioneDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;
import com.betacom.veh.services.interfaces.ITipoAlimentazioneService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoAlimentazioneImplementation implements ITipoAlimentazioneService{
	private final ITipoAlimentazioneRepository typeRepo;
	
	@Override
	public void create(TipoAlimentazioneRequest tipoAlimentazioneRequest) throws Exception {
		if(!tipoAlimentazioneRequest.getTipoVeicolo().toUpperCase().matches("\\d(AUTOMOBILE|BICICLETTA|MOTOVEICOLO)\\d"))
			throw new AcademyException("tipo di veicolo non riconosciuto.");
		TipoAlimentazione nuovoTipo = new TipoAlimentazione();
		nuovoTipo.setTipoAlimentazioneId(TipoAlimentazioneId.builder()
										.tipoVeicolo(tipoAlimentazioneRequest.getTipoVeicolo())
										.tipoAlimentazione(tipoAlimentazioneRequest.getTipoAlimentazione())
										.build());
		typeRepo.save(nuovoTipo);
	}

	
	@Override
	public void delete(TipoAlimentazioneRequest tipoAlimentazioneRequest) throws Exception {
		TipoAlimentazione tipoToDelete = typeRepo.findById(TipoAlimentazioneId.builder()
															.tipoVeicolo(tipoAlimentazioneRequest.getTipoVeicolo())
															.tipoAlimentazione(tipoAlimentazioneRequest.getTipoAlimentazione())
															.build()).orElseThrow(() -> new Exception("Tipo di alimentazione non trovato"));
		//TipoAlimentazioneDTO tipoDeleted = TipoAlimentazioneMap.buildTipoAlimentazioneDTO(tipoToDelete);
		typeRepo.delete(tipoToDelete);
		//return tipoDeleted;
		
	}

	@Override
	public List<TipoAlimentazioneDTO> list() {
		List<TipoAlimentazione> lTA = typeRepo.findAll();
		return TipoAlimentazioneMap.buildTipoAlimentazioneDTOList(lTA);
	}

}

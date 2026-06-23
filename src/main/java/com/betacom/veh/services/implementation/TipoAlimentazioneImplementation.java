package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
		String tipoVeicolo = tipoAlimentazioneRequest.getTipoVeicolo();
		tipoVeicolo = Optional.ofNullable(tipoVeicolo).map(String::trim).map(String::toUpperCase).orElse(null);
		if(!tipoVeicolo.matches("\\b(AUTOMOBILE|BICICLETTA|MOTOVEICOLO)\\b"))
			throw new AcademyException("tipo di veicolo non riconosciuto.");
		TipoAlimentazione nuovoTipo = new TipoAlimentazione();
		String tipoAlimentazione = tipoAlimentazioneRequest.getTipoAlimentazione();
		tipoAlimentazione = Optional.ofNullable(tipoAlimentazione)
				.map(StringUtils::normalizeSpace).map(String::toUpperCase)
				.map(t -> t.replace(' ','_')).orElse(null);
		nuovoTipo.setTipoAlimentazioneId(TipoAlimentazioneId.builder()
										.tipoVeicolo(tipoVeicolo)
										.tipoAlimentazione(tipoAlimentazione)
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

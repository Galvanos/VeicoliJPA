package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.TipoAlimentazioneRequest;
import com.betacom.veh.dto.output.TipoAlimentazioneDTO;

public interface ITipoAlimentazioneService {

	void create(TipoAlimentazioneRequest tipoAlimentazioneRequest) throws Exception;
	void delete(TipoAlimentazioneRequest tipoAlimentazioneRequest) throws Exception;

	List<TipoAlimentazioneDTO> list();
}

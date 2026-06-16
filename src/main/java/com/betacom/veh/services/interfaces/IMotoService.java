package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.output.MotoDTO;

public interface IMotoService {
	final String TIPO_VEICOLO = "moto";
	void create(MotoRequest req) throws Exception;
	void update(MotoRequest req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<MotoDTO> list() throws Exception;
	MotoDTO getById(Integer id) throws Exception;
	


}

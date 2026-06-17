package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.output.MotoDTO;

public interface IMotoService {
	public static final String TIPO_VEICOLO = "MOTO";
	MotoDTO create(MotoRequest req) throws Exception;
	MotoDTO update(MotoRequest req) throws Exception;
	MotoDTO delete(Integer id) throws Exception;
	
	List<MotoDTO> list() throws Exception;
	MotoDTO getById(Integer id) throws Exception;
	


}

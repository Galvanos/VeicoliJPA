package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.dto.output.BiciDTO;

public interface IBiciService {
	BiciDTO create(BiciRequest req) throws Exception;
	BiciDTO update(BiciRequest req) throws Exception;
	BiciDTO delete(Integer id) throws Exception;
	
	List<BiciDTO> list() throws Exception;
	BiciDTO getById(Integer id) throws Exception;
}

package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.output.AutomobileDTO;

public interface IAutomobileService {
	void insert(AutomobileRequest req) throws Exception;
	void update(AutomobileRequest req) throws Exception;
	void delete(Integer id) throws Exception;
	
	List<AutomobileDTO> list() throws Exception;
	AutomobileDTO getById(Integer id) throws Exception;
	
}

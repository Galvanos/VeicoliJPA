package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.ListVeicoloRequest;
import com.betacom.veh.dto.output.VeicoloDTO;

public interface IVeicoloService {

	List<VeicoloDTO> findAll(ListVeicoloRequest filters);
	VeicoloDTO findById(Integer id);
}

package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.output.VeicoloDTO;

public interface IVeicoloService {

	List<VeicoloDTO> findAll();
	VeicoloDTO findById(Integer id);
}

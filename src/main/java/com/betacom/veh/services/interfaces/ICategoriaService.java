package com.betacom.veh.services.interfaces;

import java.util.List;

import com.betacom.veh.dto.input.CategoriaRequest;
import com.betacom.veh.dto.output.CategoriaDTO;

public interface ICategoriaService {
	void create(CategoriaRequest categoriaRequest) throws Exception;
	void delete(CategoriaRequest categoriaRequest) throws Exception;

	List<CategoriaDTO> list();
}

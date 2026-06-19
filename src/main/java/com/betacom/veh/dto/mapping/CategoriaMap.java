package com.betacom.veh.dto.mapping;

import java.util.List;


import com.betacom.veh.dto.output.CategoriaDTO;
import com.betacom.veh.models.Categoria;

public class CategoriaMap {

	public static CategoriaDTO buildCategoriaDTO(Categoria categoria) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setTipoVeicolo(categoria.getCategoriaId().getTipoVeicolo());
		categoriaDTO.setCategoria(categoria.getCategoriaId().getCategoria());
		return categoriaDTO;
	}
	
	public static List<CategoriaDTO> buildCategoriaDTOList(List<Categoria> list){
		 return list.stream().map(CategoriaMap::buildCategoriaDTO).toList();
	}
}

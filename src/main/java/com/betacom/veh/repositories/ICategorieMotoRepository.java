package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.CategorieMoto;

public interface ICategorieMotoRepository  extends JpaRepository<CategorieMoto, String>{
	Boolean existsByCategoria(String categoria);
}

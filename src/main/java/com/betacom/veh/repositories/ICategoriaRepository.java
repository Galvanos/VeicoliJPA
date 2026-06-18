package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.Categoria;
import com.betacom.veh.models.CategoriaId;

public interface ICategoriaRepository extends JpaRepository<Categoria, CategoriaId>{
	Boolean existsByCategoriaId(CategoriaId categoriaId);
}

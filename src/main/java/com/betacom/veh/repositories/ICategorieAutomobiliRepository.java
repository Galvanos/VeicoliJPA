package com.betacom.veh.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.CategorieAutomobili;

public interface ICategorieAutomobiliRepository extends JpaRepository<CategorieAutomobili, String>{
	Boolean existsByCategoria(String categoria);
}

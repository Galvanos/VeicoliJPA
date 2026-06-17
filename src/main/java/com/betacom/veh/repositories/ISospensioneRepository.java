package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.TipoSospensione;

public interface ISospensioneRepository extends JpaRepository<TipoSospensione, String>{
	Boolean existsByTipoSospensione(String tipoSospensione);
}

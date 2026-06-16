package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.Moto;

public interface IMotoRepository extends JpaRepository<Moto, Integer>{
	Boolean existsByTarga(String targa);

}

package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.TipiAlimentazioneMotorizzati;

public interface ITipiAlimentazioneMotorizzati extends JpaRepository<TipiAlimentazioneMotorizzati, String> {
	Boolean existsByTipo(String tipo);
}

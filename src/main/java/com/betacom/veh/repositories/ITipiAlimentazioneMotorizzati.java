package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.TipoAlimentazioneMotorizzato;

public interface ITipiAlimentazioneMotorizzati extends JpaRepository<TipoAlimentazioneMotorizzato, String> {
	Boolean existsByTipo(String tipo);
}

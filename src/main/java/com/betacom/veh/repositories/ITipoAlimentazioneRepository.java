package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;

public interface ITipoAlimentazioneRepository extends JpaRepository<TipoAlimentazione, TipoAlimentazioneId>{
	Boolean existsByTipoAlimentazioneId(TipoAlimentazioneId tipoAlimentazioneId);
}

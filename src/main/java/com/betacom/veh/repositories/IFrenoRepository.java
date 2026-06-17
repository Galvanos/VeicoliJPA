package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.TipoFreno;

public interface IFrenoRepository extends JpaRepository<TipoFreno, String>{
	Boolean existsByTipo(String tipoFreno);
}

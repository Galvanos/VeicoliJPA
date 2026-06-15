package com.betacom.veh.repositories;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betacom.veh.models.Veicolo;

@Repository
public interface IVeicoloRepository extends JpaRepository<Veicolo, Integer>{

	@Query("SELECT v FROM Veicolo v WHERE v.tipoVeicolo = :tipo")
	List<Veicolo> findByCategoria(@Param("tipo") String tipo);

}

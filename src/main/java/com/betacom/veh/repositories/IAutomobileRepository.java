package com.betacom.veh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betacom.jpa.models.Socio;
import com.betacom.veh.models.Automobile;

@Repository
public interface IAutomobileRepository extends JpaRepository<Automobile, Integer> {
    Boolean existsByTarga(String targa);

    List<Automobile> findByMarca(String marca);
    @Query("SELECT a FROM Automobile a WHERE a.cc > :cilindrata")
    List<Automobile> findPotenti(@Param("cilindrata") Integer cc);

    List<Automobile> findByModello(String modello);
}
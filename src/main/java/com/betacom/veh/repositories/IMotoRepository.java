package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.Automobile;

public interface IMotoRepository extends JpaRepository<Automobile, Integer>{

}

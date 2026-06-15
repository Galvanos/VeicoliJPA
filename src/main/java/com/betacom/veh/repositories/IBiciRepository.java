package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.Automobile;

public interface IBiciRepository extends JpaRepository<Automobile, Integer>{

}

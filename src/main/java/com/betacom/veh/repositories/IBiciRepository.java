package com.betacom.veh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.veh.models.Automobile;
import com.betacom.veh.models.Bici;

public interface IBiciRepository extends JpaRepository<Bici, Integer>{

}

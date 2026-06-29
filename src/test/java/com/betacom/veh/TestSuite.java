package com.betacom.veh;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.veh.alimentazione.TestTipoAlimentazioneController;
import com.betacom.veh.automobile.AutomobileTest;
import com.betacom.veh.categorie.TestCategorieController;
import com.betacom.veh.veicolo.TestVeicoloController;


@Suite
@SelectClasses({
	TestCategorieController.class,
	TestTipoAlimentazioneController.class,
	AutomobileTest.class,
	TestVeicoloController.class
})
public class TestSuite {

}

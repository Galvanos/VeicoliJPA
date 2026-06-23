package com.betacom.veh;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.veh.categorie.TestCategorieController;
import com.betacom.veh.moto.TestMotoController;


@Suite
@SelectClasses({
	TestCategorieController.class,
	TestMotoController.class
})
public class TestSuite {

}

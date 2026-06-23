package com.betacom.veh;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.veh.automobile.AutomobileTest;
import com.betacom.veh.categorie.TestCategorieController;


@Suite
@SelectClasses({
	TestCategorieController.class,
	AutomobileTest.class
})
public class TestSuite {

}

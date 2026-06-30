package com.betacom.veh;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.betacom.veh.categorie.TestCategorieController;


@Suite
@SelectClasses({
	TestCategorieController.class,
	TestBici.class
})
public class TestSuite {

}

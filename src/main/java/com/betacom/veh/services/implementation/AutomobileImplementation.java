package com.betacom.veh.services.implementation;

import java.util.List;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.repositories.IAutomobileRepository;
import com.betacom.veh.services.interfaces.IAutomobileService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AutomobileImplementation implements IAutomobileService{
	
	private final IAutomobileRepository automobileRepository;

	@Override
	public AutomobileDTO insert(AutomobileRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomobileDTO update(AutomobileRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomobileDTO delete(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AutomobileDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AutomobileDTO getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

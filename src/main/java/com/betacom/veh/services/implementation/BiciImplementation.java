package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Bici;
import com.betacom.veh.repositories.IBiciRepository;
import com.betacom.veh.repositories.IFrenoRepository;
import com.betacom.veh.repositories.ISospensioneRepository;
import com.betacom.veh.services.interfaces.IBiciService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BiciImplementation implements IBiciService{

	private final IBiciRepository biciR;
	private final IFrenoRepository frenR;
	private final ISospensioneRepository sospR;
	
	@Override
	public BiciDTO create(BiciRequest req) throws Exception {
		
		Bici bici = new Bici();
		if(req.getNumeroRapporti() <= 1 || req.getNumeroRapporti() >= 33)
			throw new AcademyException("Numero rapporti non valido: " + req.getNumeroRapporti());
		bici.setNumeroRapporti(req.getNumeroRapporti());
		if (!frenR.existsByTipo(req.getTipoFreno().trim().toUpperCase()))
			throw new AcademyException("Tipo di freno non trovato");
		bici.setTipoFreno(req.getTipoFreno().trim().toUpperCase());
		if (!sospR.existsByTipo(req.getTipoSospensione().trim().toUpperCase()))
			throw new AcademyException("Tipo di sospensione non trovato");
		bici.setTipoSospensione(req.getTipoSospensione().trim().toUpperCase());
		bici.setPieghevole(req.getPieghevole());
		
		return null;
	}

	@Override
	public BiciDTO update(BiciRequest req) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BiciDTO delete(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BiciDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BiciDTO getById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

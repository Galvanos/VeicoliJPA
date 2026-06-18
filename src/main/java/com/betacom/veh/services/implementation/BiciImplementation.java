package com.betacom.veh.services.implementation;

import java.util.List;

import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.mapping.BiciMap;
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
		if (!frenR.existsByTipoFreno(req.getTipoFreno().trim().toUpperCase()))
			throw new AcademyException("Tipo di freno non trovato");
		bici.setTipoFreno(req.getTipoFreno().trim().toUpperCase());
		if (!sospR.existsByTipoSospensione(req.getTipoSospensione().trim().toUpperCase()))
			throw new AcademyException("Tipo di sospensione non trovato");
		bici.setTipoSospensione(req.getTipoSospensione().trim().toUpperCase());
		bici.setPieghevole(req.getPieghevole());
		bici.setTipoAlimentazione(Alimentazione(req.getTipoAlimentazione()));
		
		BiciDTO biciDto = BiciMap.buildBiciDTO(biciR.save(bici));
		
		return biciDto;
	}

	@Override
	public BiciDTO update(BiciRequest req) throws Exception {
		Bici bici = biciR.findById(req.getId()).orElseThrow(() -> new AcademyException("Bici non trovata"));
		
		
		
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
	
	private String Alimentazione(String alimentazione) {
		
		if (alimentazione.trim().toUpperCase().matches("\\b(ELETTRICO|ELETTRICA)\\b")) {
			return alimentazione = "ELETTRICA";
		}else if (alimentazione.trim().toUpperCase().matches("\\b(MUSCOLARE|MANUALE)\\b")) {
			return alimentazione = "MANUALE";
		}else {
			throw new AcademyException("Tipo alimentazione non valido.");
		}
	}

}

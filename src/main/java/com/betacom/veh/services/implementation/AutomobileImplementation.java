package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.mapping.AutomobileMap;
import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.models.Automobile;
import com.betacom.veh.repositories.IAutomobileRepository;
import com.betacom.veh.services.interfaces.IAutomobileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutomobileImplementation implements IAutomobileService{
	
	private final IAutomobileRepository carRepo;

	@Override
	public AutomobileDTO create(AutomobileRequest req) throws Exception {

		Automobile car = new Automobile();
		if(carRepo.existsByTarga(req.getTarga()))
			throw new Exception("Targa giá presente nel db;");
		car.setTarga(req.getTarga());
		car.setNumeroRuote(req.getNumeroRuote());
		car.setAnnoProduzione(req.getAnnoProduzione());
		car.setCategoria(req.getCategoria());
		car.setCc(req.getCc());
		car.setColore(req.getColore());
		car.setMarca(req.getMarca());
		car.setNumeroPorte(req.getNumeroPorte());
		car.setTipoAlimentazione(req.getTipoAlimentazione());
		car.setModello(req.getModello());
		car.setTipoVeicolo("Automobile");
		
		AutomobileDTO carDto = AutomobileMap.buildAutomobileDTO(carRepo.save(car));
		return carDto;
	}

	@Override
	public AutomobileDTO update(AutomobileRequest req) throws Exception {
		Automobile car = carRepo.findById(req.getId()).orElseThrow(() -> new Exception("Automobile non trovata"));
		if(req.getTarga() != null && !req.getTarga().equalsIgnoreCase(car.getTarga())) {
			if(carRepo.existsByTarga(req.getTarga())) throw new Exception("Targa giá presente nel db");
			car.setTarga(req.getTarga());
		}
		Optional.ofNullable(req.getNumeroRuote()).ifPresent(car::setNumeroRuote);
		Optional.ofNullable(req.getAnnoProduzione()).ifPresent(car::setAnnoProduzione);
		Optional.ofNullable(req.getCategoria()).ifPresent(car::setCategoria);
		Optional.ofNullable(req.getCc()).ifPresent(car::setCc);
		Optional.ofNullable(req.getColore()).ifPresent(car::setColore);
		Optional.ofNullable(req.getMarca()).ifPresent(car::setMarca);
		Optional.ofNullable(req.getNumeroPorte()).ifPresent(car::setNumeroPorte);
		Optional.ofNullable(req.getTipoAlimentazione()).ifPresent(car::setTipoAlimentazione);
		Optional.ofNullable(req.getModello()).ifPresent(car::setModello);

		AutomobileDTO carDto = AutomobileMap.buildAutomobileDTO(carRepo.save(car));
		return carDto;
	}

	@Override
	public AutomobileDTO delete(Integer id) throws Exception {
		Automobile car = carRepo.findById(id).orElseThrow(() -> new Exception("Auto non trovata"));
		AutomobileDTO carDto = AutomobileMap.buildAutomobileDTO(car);
		carRepo.delete(car);
		return carDto;
	}

	@Override
	public AutomobileDTO getById(Integer id) throws Exception {
		Automobile car = carRepo.findById(id).orElseThrow(() -> new Exception("Auto non trovata"));
		return AutomobileMap.buildAutomobileDTO(car);
	}

	@Override
	public List<AutomobileDTO> list() throws Exception {
		List<Automobile> lA = carRepo.findAll();
		return AutomobileMap.buildAutomobileDTOList(lA);
	}

}

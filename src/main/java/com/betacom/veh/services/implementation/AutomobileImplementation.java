package com.betacom.veh.services.implementation;

import java.time.LocalDate;
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
		if(req.getNumeroRuote() < 1 || req.getNumeroRuote() > 99)
			throw new Exception("Numero di ruote non valido;");
		car.setNumeroRuote(req.getNumeroRuote());
		if(req.getAnnoProduzione() > LocalDate.now().getYear() || LocalDate.now().getYear() - req.getAnnoProduzione() > 20)
			throw new Exception("Anno di produzione non valido o troppo poco recente;");
		car.setAnnoProduzione(req.getAnnoProduzione());
		car.setCategoria(req.getCategoria()); // implementare check da database
		car.setCc(req.getCc());
		car.setColore(req.getColore());
		car.setMarca(req.getMarca());
		if(req.getNumeroRuote() < 1 || req.getNumeroRuote() > 10)
			throw new Exception("Numero di porte non valido;");
		car.setNumeroPorte(req.getNumeroPorte());
		car.setTipoAlimentazione(req.getTipoAlimentazione()); // implementare check da database
		car.setModello(req.getModello());
		car.setTipoVeicolo("AUTOMOBILE");
		
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

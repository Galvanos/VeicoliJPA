package com.betacom.veh.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.mapping.AutomobileMap;
import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.models.Automobile;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.IAutomobileRepository;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;
import com.betacom.veh.services.interfaces.IAutomobileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutomobileImplementation implements IAutomobileService{
	private final ICategoriaRepository catRepo;
	private final ITipoAlimentazioneRepository typeRepo;
	private final IAutomobileRepository carRepo;
	private final static String PATTERN_TARGA_AUTO = "^[a-zA-Z]{2}[0-9]{3}[a-zA-Z]{2}$";
	private final static String tipoVeicolo = "AUTOMOBILE";

	@Override
	public AutomobileDTO create(AutomobileRequest req) throws Exception {
		validateRequestValues(req);
		
		Automobile car = new Automobile();

		car.setId(null);		
		car.setTarga(req.getTarga().toUpperCase());
		car.setNumeroRuote(req.getNumeroRuote());
		car.setAnnoProduzione(req.getAnnoProduzione());
		car.setCategoria(req.getCategoria());
		car.setCc(req.getCc());
		car.setColore(req.getColore());
		car.setMarca(req.getMarca());
		car.setNumeroPorte(req.getNumeroPorte());
		car.setTipoAlimentazione(req.getTipoAlimentazione());
		car.setModello(req.getModello());
		car.setTipoVeicolo(tipoVeicolo);
		
		AutomobileDTO carDto = AutomobileMap.buildAutomobileDTO(carRepo.save(car));
		return carDto;
	}
  
	@Override
	public AutomobileDTO update(AutomobileRequest req) throws Exception {
		Automobile car = carRepo.findById(req.getId()).orElseThrow(() -> new Exception("Automobile non trovata"));
		validateRequestValues(req);
		if(req.getTarga() != null && !req.getTarga().equalsIgnoreCase(car.getTarga())) {
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

	private void validateRequestValues(AutomobileRequest req) throws Exception{
		
		Optional.ofNullable(req.getTarga()).ifPresent(targa -> {
			if(carRepo.existsByTarga(targa) || !targa.matches(PATTERN_TARGA_AUTO))
				throw new RuntimeException("Targa non valida o giá presente nel db.");
		});		
		Optional.ofNullable(req.getNumeroRuote()).ifPresent(numeroRuote -> {
			if(numeroRuote < 1 || numeroRuote > 99)
				throw new RuntimeException("Numero ruote non valido.");
		});
		Optional.ofNullable(req.getAnnoProduzione()).ifPresent(annoProduzione -> {
			if(annoProduzione > LocalDate.now().getYear() || LocalDate.now().getYear() - annoProduzione > 20)
				throw new RuntimeException("Anno produzione non valido o troppo vecchio.");
		}); 
		Optional.ofNullable(req.getNumeroPorte()).ifPresent(numeroPorte -> {
			if(numeroPorte < 1 || numeroPorte > 10)
				throw new RuntimeException("Numero di porte non valido.");
		});
		Optional.ofNullable(req.getCategoria()).ifPresent(categoria -> {
			if(!catRepo.existsByCategoriaId(CategoriaId.builder().tipoVeicolo(tipoVeicolo).categoria(categoria.toUpperCase()).build()))
				throw new RuntimeException("Categoria non valida.");
			else req.setCategoria(categoria.toUpperCase());
		});
		Optional.ofNullable(req.getTipoAlimentazione()).ifPresent(tipoAlimentazione -> {
			if(!typeRepo.existsByTipoAlimentazioneId(TipoAlimentazioneId.builder().tipoVeicolo(tipoVeicolo).tipoAlimentazione(tipoAlimentazione.toUpperCase()).build()))
				throw new RuntimeException("Tipo di alimentazione non valido.");
			else req.setTipoAlimentazione(tipoAlimentazione.toUpperCase());
		});
			

	}
}

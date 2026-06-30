package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.mapping.BiciMap;
import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Bici;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.IBiciRepository;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.repositories.IFrenoRepository;
import com.betacom.veh.repositories.ISospensioneRepository;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;
import com.betacom.veh.services.interfaces.IBiciService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BiciImplementation implements IBiciService{

	private final IBiciRepository biciR;
	private final IFrenoRepository frenR;
	private final ICategoriaRepository catRepo;
	private final ISospensioneRepository sospR;
	private final ITipoAlimentazioneRepository typeRepo;
	private final String tipoVeicolo = "BICICLETTA";
	
	@Transactional
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
		if (req.getNumeroRuote() <= 2 || req.getNumeroRuote() >= 4)
			throw new AcademyException("Numero di ruote non valido: " + req.getNumeroRuote());
		bici.setNumeroRuote(req.getNumeroRuote());
		if (!typeRepo.existsByTipoAlimentazioneId(TipoAlimentazioneId.builder().tipoVeicolo("BICICLETTA").tipoAlimentazione(req.getTipoAlimentazione()).build())) {
			throw new AcademyException("Tipo di Alimentazione non valido: " + req.getTipoAlimentazione());
		}
		bici.setTipoAlimentazione(Alimentazione(req.getTipoAlimentazione()));
		if (!catRepo.existsByCategoriaId(CategoriaId.builder().tipoVeicolo("BICICLETTA").categoria(req.getCategoria()).build())) {
			throw new AcademyException("Tipo di Categoria non valido: " + req.getTipoAlimentazione());
		}
		bici.setTipoAlimentazione(Alimentazione(req.getTipoAlimentazione()));
		
		BiciDTO biciDto = BiciMap.buildBiciDTO(biciR.save(bici));
		
		return biciDto;
	}

	@Transactional
	@Override
	public BiciDTO update(BiciRequest req) throws Exception {
		Bici bici = biciR.findById(req.getId()).orElseThrow(() -> new AcademyException("Bici non trovata"));
		
		Optional.ofNullable(req.getNumeroRapporti()).ifPresent(bici::setNumeroRapporti);
		Optional.ofNullable(req.getTipoFreno()).ifPresent(bici::setTipoFreno);
		Optional.ofNullable(req.getTipoSospensione()).ifPresent(bici::setTipoSospensione);
		Optional.ofNullable(req.getPieghevole()).ifPresent(bici::setPieghevole);
		
		BiciDTO biciDto = BiciMap.buildBiciDTO(biciR.save(bici));
		
		return biciDto;
	}

	@Transactional
	@Override
	public BiciDTO delete(Integer id) throws Exception {
		Bici bici = biciR.findById(id).orElseThrow(() -> new AcademyException("Bici non trovata"));
		
		biciR.delete(bici);
		BiciDTO biciDto = BiciMap.buildBiciDTO(bici);
		
		return biciDto;
	}

	@Override
	public List<BiciDTO> list() throws Exception {
		List<Bici> listaBici = biciR.findAll();
		return BiciMap.buildBiciDTOList(listaBici);
	}

	@Override
	public BiciDTO getById(Integer id) throws Exception {
		
		Bici bici = biciR.findById(id).orElseThrow(() -> new AcademyException("Bici non trovata"));

		return BiciMap.buildBiciDTO(bici);
	}
	
	private String Alimentazione(String alimentazione) {
		
		if(!typeRepo.existsByTipoAlimentazioneId(TipoAlimentazioneId.builder()
				.tipoVeicolo(tipoVeicolo)
				.tipoAlimentazione(alimentazione.toUpperCase())
				.build()))
			return alimentazione.toUpperCase();
		else
			throw new AcademyException("Tipo alimentazione non valido.");
	}

}

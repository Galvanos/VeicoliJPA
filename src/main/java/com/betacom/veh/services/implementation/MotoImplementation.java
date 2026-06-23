package com.betacom.veh.services.implementation;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.mapping.MotoMap;
import com.betacom.veh.dto.output.MotoDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.models.Moto;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.repositories.IMotoRepository;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;
import com.betacom.veh.services.interfaces.IMotoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MotoImplementation implements IMotoService{
	private final IMotoRepository repMoto;
	private final ITipoAlimentazioneRepository typeRepo;
	private final ICategoriaRepository catRepo;
	private final static String PATTERN_TARGA_MOTO = "^[a-zA-Z]{2}[0-9]{5,6}$";
	private final static String tipoVeicolo = "MOTOVEICOLO";


	@Override
	@Transactional
	public MotoDTO create(MotoRequest req) throws Exception {
		log.debug("create moto :{}", req);
		validateRequestValues(req);
		String targa = req.getTarga().trim().toUpperCase();
		req.setTarga(targa);
		
		if (repMoto.existsByTarga(targa))
			throw new AcademyException("Eccezione targa moto esiste già");
		
		Moto moto = new Moto();
		
		moto.setId(null);
		moto.setTarga(targa);
		moto.setCc(req.getCc());	
		
		moto.setNumeroRuote(req.getNumeroRuote());
		moto.setTipoAlimentazione(
				req.getTipoAlimentazione().trim().toUpperCase());
		moto.setCategoria(req.getCategoria());
		moto.setColore(req.getColore().trim().toUpperCase());
		moto.setMarca(req.getMarca());
		moto.setAnnoProduzione(req.getAnnoProduzione());
		moto.setModello(req.getModello());
		moto.setTipoVeicolo(TIPO_VEICOLO);

		MotoDTO motoDto = MotoMap.buildMotoDTO(repMoto.save(moto));
		return motoDto;
	}
	
	@Override
	public List<MotoDTO> list() throws Exception {
		log.debug("list moto :{}");
		List<Moto> lMoto = repMoto.findAll();
		return MotoMap.buildMotoDTOList(lMoto);
	}
	
	@Override
	public MotoDTO getById(Integer id) throws Exception {
		log.debug("getById moto per id:{}", id);
		Moto moto = repMoto.findById(id)
				.orElseThrow(() -> new AcademyException("Id moto non trovato"));
		return MotoMap.buildMotoDTO(moto);
	}
	
	@Override
	@Transactional
	public MotoDTO update(MotoRequest req) throws Exception {
	    Moto moto = repMoto.findById(req.getId())
	            .orElseThrow(() -> new AcademyException("Moto non trovata"));

	    validateRequestValues(req);

	    if (req.getTarga() != null) moto.setTarga(req.getTarga().trim().toUpperCase());
	    if (req.getCc() != null) moto.setCc(req.getCc());
	   
		Optional.ofNullable(req.getColore()).ifPresent(moto::setColore);
	    Optional.ofNullable(req.getTipoAlimentazione()).ifPresent(moto::setTipoAlimentazione);
	    Optional.ofNullable(req.getNumeroRuote()).ifPresent(moto::setNumeroRuote);
	    Optional.ofNullable(req.getAnnoProduzione()).ifPresent(moto::setAnnoProduzione);
	    Optional.ofNullable(req.getCategoria()).ifPresent(moto::setCategoria);
	    Optional.ofNullable(req.getMarca()).ifPresent(moto::setMarca);
	    Optional.ofNullable(req.getModello()).ifPresent(moto::setModello);

	    return MotoMap.buildMotoDTO(repMoto.save(moto));
	}
	
	@Override
	@Transactional
	public MotoDTO delete(Integer id) throws Exception {
		log.debug("delete moto con id:{}", id);
		Moto moto = repMoto.findById(id)
				.orElseThrow(() -> new AcademyException("Id moto non trovato"));
		MotoDTO motoDto = MotoMap.buildMotoDTO(moto);
		repMoto.delete(moto);
		return motoDto;
		
	}
	
	private void validateRequestValues(MotoRequest req) throws Exception{
		
		Optional.ofNullable(req.getTarga()).ifPresent(targa -> {
			if(repMoto.existsByTarga(targa) || !targa.matches(PATTERN_TARGA_MOTO))
				throw new AcademyException("Targa non valida o giá presente nel db.");
		});		
		Optional.ofNullable(req.getNumeroRuote()).ifPresent(numeroRuote -> {
			if(numeroRuote < 1 || numeroRuote > 99)
				throw new AcademyException("Numero ruote non valido.");
		});
		Optional.ofNullable(req.getAnnoProduzione()).ifPresent(annoProduzione -> {
			if(annoProduzione > LocalDate.now().getYear() || LocalDate.now().getYear() - annoProduzione > 20)
				throw new AcademyException("Anno produzione non valido o troppo vecchio.");
		}); 
		
		Optional.ofNullable(req.getCategoria()).ifPresent(categoria -> {
			if(!catRepo.existsByCategoriaId(CategoriaId.builder()
					.tipoVeicolo(tipoVeicolo)
					.categoria(categoria.toUpperCase())
					.build()))
				throw new AcademyException("Categoria non valida.");
		});
		Optional.ofNullable(req.getTipoAlimentazione()).ifPresent(tipoAlimentazione -> {
			if(!typeRepo.existsByTipoAlimentazioneId(TipoAlimentazioneId.builder()
					.tipoVeicolo(tipoVeicolo)
					.tipoAlimentazione(tipoAlimentazione.toUpperCase())
					.build()))
				throw new AcademyException("Tipo di alimentazione non valido.");
		});
			

	}
}

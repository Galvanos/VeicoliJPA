package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.input.VeicoloRequest;
import com.betacom.veh.dto.mapping.MotoMap;
import com.betacom.veh.dto.output.MotoDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Moto;
import com.betacom.veh.repositories.IMotoRepository;
import com.betacom.veh.services.interfaces.IMotoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class MotoImplementation implements IMotoService{
	private final IMotoRepository repMoto;
	private final VeicoloRequest requestVeicolo;
	final String TIPO_VEICOLO = "moto";

	@Override
	@Transactional
	public void create(MotoRequest req) throws Exception {
		log.debug("create moto :{}", req);
		if (repMoto.existsByTarga(req.getTarga().trim().toUpperCase()))
			throw new AcademyException("Eccezione targa moto esiste già");
		Moto moto = new Moto();

		moto.setTarga(Optional.ofNullable(req.getTarga().trim().toUpperCase())
				.orElseThrow(() -> new AcademyException("Eccezione targa moto")));

		moto.setCc(Optional.ofNullable(req.getCc())
				.orElseThrow(() -> new AcademyException("Eccezione cc moto")));
		
		if (req.getId() == null) throw new AcademyException("Eccezione id moto");
		moto.setId(req.getId());
		
		if (req.getNumeroRuote() == null) throw new AcademyException("Eccezione numero ruote moto");
		moto.setNumeroRuote(req.getNumeroRuote());
		
		moto.setTipoAlimentazione(Optional.ofNullable(req.getTipoAlimentazione().trim().toUpperCase())
				.orElseThrow(() -> new AcademyException("Eccezione tipo alimentazione moto")));

		moto.setCategoria(Optional.ofNullable(req.getCategoria())
				.orElseThrow(() -> new AcademyException("Eccezione categoria moto")));
		
		moto.setColore(Optional.ofNullable(req.getColore().trim().toUpperCase())
				.orElseThrow(() -> new AcademyException("Eccezione colore moto")));

		moto.setMarca(Optional.ofNullable(req.getMarca())
				.orElseThrow(() -> new AcademyException("Eccezione marca moto")));

		if (req.getAnnoProduzione() == null) throw new AcademyException("Eccezione anno produzione moto");
		moto.setAnnoProduzione(req.getAnnoProduzione());
		
		moto.setModello(Optional.ofNullable(req.getModello())
				.orElseThrow(() -> new AcademyException("Eccezione modello moto")));

		repMoto.save(moto);
	}
	
	@Override
	@Transactional
	public List<MotoDTO> list() throws Exception {
		log.debug("list moto :{}");
		List<Moto> lMoto = repMoto.findAll();
		return MotoMap.buildMotoDTOList(lMoto);
	}
	
	@Override
	@Transactional
	public MotoDTO getById(Integer id) throws Exception {
		log.debug("getById moto per id:{}", id);
		Moto moto = repMoto.findById(id)
				.orElseThrow(() -> new AcademyException("Id moto non trovato"));
		return MotoMap.buildMotoDTO(moto);
	}
	@Override
	@Transactional
	public void update(MotoRequest req) throws Exception {
		log.debug("update moto :{}", req);
		Moto moto = repMoto.findById(requestVeicolo.getId())
				.orElseThrow(() -> new AcademyException("attiv.ntfnd"));
		
		if (req.getTarga() != null) {
			if (repMoto.existsByTarga(req.getTarga().trim().toUpperCase()))
				throw new AcademyException("Targa moto già presente");
			moto.setTarga(req.getTarga().translateEscapes().toUpperCase());
		}
		
		if (req.getCc() != null) {
			moto.setCc(req.getCc());
		}
		
		repMoto.save(moto);
	}
	
	@Override
	@Transactional
	public void delete(Integer id) throws Exception {
		log.debug("delete moto con id:{}", id);
		Moto moto = repMoto.findById(id)
				.orElseThrow(() -> new AcademyException("Id moto non trovato"));
		repMoto.delete(moto);
	}
}

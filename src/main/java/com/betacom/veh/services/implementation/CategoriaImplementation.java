package com.betacom.veh.services.implementation;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.betacom.veh.dto.input.CategoriaRequest;
import com.betacom.veh.dto.mapping.CategoriaMap;
import com.betacom.veh.dto.output.CategoriaDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Categoria;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.services.interfaces.ICategoriaService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoriaImplementation implements ICategoriaService{
	private final ICategoriaRepository catRepo;
	
	@Override
	public void create(CategoriaRequest categoriaRequest) throws Exception {
		String tipoVeicolo = categoriaRequest.getTipoVeicolo();
		//il null viene già verificato in fase di validazione
		tipoVeicolo = Optional.ofNullable(tipoVeicolo).map(String::trim).map(String::toUpperCase).orElse(null);
		if(!tipoVeicolo.matches("\\b(AUTOMOBILE|BICICLETTA|MOTOVEICOLO)\\b"))
			throw new AcademyException("tipo di veicolo non riconosciuto.");
		Categoria nuovaCategoria = new Categoria();
		String categoria = categoriaRequest.getCategoria();
		//normalizzazione della categoria, il null viene già verificato in fase di validazione
		categoria = Optional.ofNullable(categoria)
				.map(StringUtils::normalizeSpace)
				.map(String::toUpperCase)
				.map(t -> t.replace(" ", "_"))
				.orElse(null);
		nuovaCategoria.setCategoriaId(CategoriaId.builder()
										.tipoVeicolo(tipoVeicolo)
										.categoria(categoria)
										.build());
		catRepo.save(nuovaCategoria);
	}

	@Override
	public void delete(CategoriaRequest categoriaRequest) throws Exception {
		Categoria categoriaToDelete = catRepo.findById(CategoriaId.builder()
				.tipoVeicolo(categoriaRequest.getTipoVeicolo())
				.categoria(categoriaRequest.getCategoria())
				.build()).orElseThrow(() -> new AcademyException("Categoria non trovata"));
		//TipoAlimentazioneDTO tipoDeleted = TipoAlimentazioneMap.buildTipoAlimentazioneDTO(tipoToDelete);
		catRepo.delete(categoriaToDelete);	
		//return tipoDeleted;
	}

	@Override
	public List<CategoriaDTO> list() {
		List<Categoria> lC = catRepo.findAll();
		return CategoriaMap.buildCategoriaDTOList(lC);
	}

}

package com.betacom.veh.services.implementation;

import java.util.List;

import com.betacom.veh.dto.input.CategoriaRequest;
import com.betacom.veh.dto.output.CategoriaDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Categoria;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.services.interfaces.ICategoriaService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoriaImplementation implements ICategoriaService{
	private final ICategoriaRepository catRepo;
	
	@Override
	public void create(CategoriaRequest categoriaRequest) throws Exception {
		if(!categoriaRequest.getTipoVeicolo().toUpperCase().matches("\\d(AUTOMOBILE|BICICLETTA|MOTOVEICOLO)\\d"))
			throw new AcademyException("tipo di veicolo non riconosciuto.");
		Categoria nuovaCategoria = new Categoria();
		nuovaCategoria.setCategoriaId(CategoriaId.builder()
										.tipoVeicolo(categoriaRequest.getTipoVeicolo())
										.categoria(categoriaRequest.getCategoria())
										.build());
		catRepo.save(nuovaCategoria);
	}

	@Override
	public void delete(CategoriaRequest categoriaRequest) throws Exception {
		Categoria categoriaToDelete = catRepo.findById(CategoriaId.builder()
				.tipoVeicolo(categoriaRequest.getTipoVeicolo())
				.categoria(categoriaRequest.getCategoria())
				.build()).orElseThrow(() -> new Exception("Categoria non trovata"));
		//TipoAlimentazioneDTO tipoDeleted = TipoAlimentazioneMap.buildTipoAlimentazioneDTO(tipoToDelete);
		catRepo.delete(categoriaToDelete);		
	}

	@Override
	public List<CategoriaDTO> list() {
		// TODO Auto-generated method stub
		return null;
	}

}

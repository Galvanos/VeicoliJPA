package com.betacom.veh.services.implementation;

import com.betacom.veh.controllers.exceptionhandler.ExceptionManager;
import com.betacom.veh.repositories.IVeicoloRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.betacom.veh.services.interfaces.IVeicoloService;
import com.betacom.veh.utils.Utils;

import jakarta.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.betacom.veh.dto.input.ListVeicoloRequest;
import com.betacom.veh.dto.mapping.VeicoloMap;
import com.betacom.veh.dto.output.VeicoloDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.Automobile;
import com.betacom.veh.models.Bici;
import com.betacom.veh.models.Moto;
import com.betacom.veh.models.Veicolo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VeicoloImplementation implements IVeicoloService{

	private final IVeicoloRepository veicoloRepository;

	
	@Override
	public List<VeicoloDTO> findAll(ListVeicoloRequest filters) {
		if(filters == null) {
			return VeicoloMap.buildVeicoloDTOList(
		            veicoloRepository.findAll());
		}else {
			Specification<Veicolo> specification = buildSpecification(filters);
			return VeicoloMap.buildVeicoloDTOList(
		            veicoloRepository.findAll(specification));
		}
		
	}

	@Override
	public VeicoloDTO findById(Integer id) {
		 Veicolo veicolo = veicoloRepository.findById(id)
		            .orElseThrow(() -> new AcademyException("Veicolo non trovato"));

		    return VeicoloMap.buildVeicoloDTO(veicolo);
	}
	
	private Specification<Veicolo> buildSpecification(ListVeicoloRequest filter){
		
		List<Specification<Veicolo>> filterSpec = new LinkedList<>();
		
		List<Integer> annoProduzioneList = filter.getAnnoProduzione();
		
		addIntegerParamToSpecificationList(filterSpec, annoProduzioneList, "annoProduzione");
		
		List<String> categoriaList = filter.getCategoria();
		
		addStringParamToSpecificationList(filterSpec, categoriaList, "categoria");
		
		List<Integer> ccList = filter.getCc();
		
		ccList = Optional.ofNullable(ccList).map(Utils::removeNulls).map(t -> t.isEmpty() ? null : t).orElse(null);

		if (ccList != null) {
			List<Integer> ccListFinal = ccList;
			Specification<Veicolo> ccAutomobileSpecification = Specification
					.where((root, query, criteriaBuilder) -> {
					Root<Automobile> rootAutomobile = criteriaBuilder.treat(root, Automobile.class);
					return rootAutomobile.get("cc").in(ccListFinal);
					});
			
			Specification<Veicolo> ccMotoSpecification = Specification
					.where((root, query, criteriaBuilder) -> {
					Root<Moto> rootMoto = criteriaBuilder.treat(root, Moto.class);
					return rootMoto.get("cc").in(ccListFinal);
					});
			
			Specification<Veicolo> ccSpecification = Specification.anyOf(ccAutomobileSpecification,ccMotoSpecification);
			
			filterSpec.add(ccSpecification);
		}
		
		List<String> coloreList = filter.getColore();
		
		addStringParamToSpecificationList(filterSpec, coloreList, "colore");
		
		List<Integer> idList = filter.getId();
		
		addIntegerParamToSpecificationList(filterSpec, idList, "id");
		
		List<String> marcaList = filter.getMarca();
		
		addStringParamToSpecificationList(filterSpec, marcaList, "marca");
		
		List<String> modelloList = filter.getModello();
		
		addStringParamToSpecificationList(filterSpec, modelloList, "modello");
		
		List<Integer> numeroPorteList = filter.getNumeroPorte();
		
		numeroPorteList = Optional.ofNullable(numeroPorteList).map(Utils::removeNulls).map(t -> t.isEmpty()?null:t).orElse(null);
		
		if(numeroPorteList != null) {
			List<Integer> numeroPorteListFinal = numeroPorteList;
			Specification<Veicolo> numeroPorteSpecification = Specification.where((root, query, criteriaBuilder) ->{ 
				Root<Automobile> automobileRoot = criteriaBuilder.treat(root, Automobile.class);
				return automobileRoot.get("numeroPorte").in(numeroPorteListFinal);
			});
			filterSpec.add(numeroPorteSpecification);
		}
		
		List<Integer> numeroRapportiList = filter.getNumeroRapporti();
		
		numeroRapportiList = Optional.ofNullable(numeroRapportiList).map(Utils::removeNulls).map(t -> t.isEmpty() ? null : t).orElse(null);

		if (numeroRapportiList != null) {
			List<Integer> numeroRapportiListFinal = numeroRapportiList;
			Specification<Veicolo> numeroRapportiSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Bici> biciRoot = criteriaBuilder.treat(root, Bici.class);
				return biciRoot.get("numeroRapporti").in(numeroRapportiListFinal);
			});
			filterSpec.add(numeroRapportiSpecification);
		}
		
		List<Integer> numeroRuoteList = filter.getNumeroRuote();
		
		addIntegerParamToSpecificationList(filterSpec, numeroRuoteList, "numeroRuote");

		List<Boolean> pieghevoleList = filter.getPieghevole();
		
		pieghevoleList = Optional.ofNullable(pieghevoleList).map(Utils::removeNulls).map(t -> t.isEmpty() ? null : t).orElse(null);

		if (pieghevoleList != null) {
			List<Boolean> pieghevoleListFinal = pieghevoleList;
			Specification<Veicolo> pieghevoleSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Bici> biciRoot = criteriaBuilder.treat(root, Bici.class);
				return biciRoot.get("pieghevole").in(pieghevoleListFinal);
			});
			filterSpec.add(pieghevoleSpecification);
		}
		
		List<String> targaList = filter.getTarga();
		
		targaList = Optional.ofNullable(targaList).map(Utils::removeNullsAndBlanks).map(t -> t.isEmpty()?null:t).orElse(null);
		
		if(targaList != null) {
			List<String> targaListFinal = targaList;
			Specification<Veicolo> targaAutomobileSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Automobile> automobileRoot = criteriaBuilder.treat(root, Automobile.class);
				return automobileRoot.get("targa").in(targaListFinal);
			});
			Specification<Veicolo> targaMotoSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Moto> motoRoot = criteriaBuilder.treat(root, Moto.class);
				return motoRoot.get("targa").in(targaListFinal);
			});
			
			Specification<Veicolo> targaSpecification = Specification.anyOf(targaAutomobileSpecification,targaMotoSpecification);
			
			filterSpec.add(targaSpecification);
		}
		
		List<String> tipoAlimentazioneList = filter.getTipoAlimentazione();
		
		addStringParamToSpecificationList(filterSpec, tipoAlimentazioneList, "tipoAlimentazione");
		
		List<String> tipoFrenoList = filter.getTipoFreno();
		
		tipoFrenoList = Optional.ofNullable(tipoFrenoList).map(Utils::removeNullsAndBlanks).map(t -> t.isEmpty() ? null : t).orElse(null);

		if (tipoFrenoList != null) {
			List<String> tipoFrenoListFinal = tipoFrenoList;
			Specification<Veicolo> tipoFrenoSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Bici> biciRoot = criteriaBuilder.treat(root, Bici.class);
				return biciRoot.get("tipoFreno").in(tipoFrenoListFinal);
			});
			filterSpec.add(tipoFrenoSpecification);
		}
		
		List<String> tipoSospensioneList = filter.getTipoSospensione();
		
		tipoSospensioneList = Optional.ofNullable(tipoSospensioneList).map(Utils::removeNullsAndBlanks).map(t -> t.isEmpty() ? null : t).orElse(null);

		if (tipoSospensioneList != null) {
			List<String> tipoSospensioneListFinal = tipoSospensioneList;
			Specification<Veicolo> tipoSospensioneSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Bici> biciRoot = criteriaBuilder.treat(root, Bici.class);
				return biciRoot.get("tipoSospensione").in(tipoSospensioneListFinal);
			});
			filterSpec.add(tipoSospensioneSpecification);
		}
		
		
		return Specification.allOf(filterSpec);
	}

	private void addIntegerParamToSpecificationList(List<Specification<Veicolo>> filterSpec,
			List<Integer> integerParamList, String fieldName) {
		integerParamList = Optional.ofNullable(integerParamList).map(Utils::removeNulls).map(t -> t.isEmpty()?null:t).orElse(null);
		
		if(integerParamList != null) {
			List<Integer> integerParamListFinal = integerParamList;
			Specification<Veicolo> integerParamSpecification = Specification.where((root, query, criteriaBuilder) -> 
				root.get(fieldName).in(integerParamListFinal));
			filterSpec.add(integerParamSpecification);
		}
	}

	private void addStringParamToSpecificationList(List<Specification<Veicolo>> filterSpec,
			List<String> stringParamList, String fieldName) {
		stringParamList = Optional.ofNullable(stringParamList).map(Utils::removeNullsAndBlanks).map(t -> t.isEmpty()?null:t).orElse(null);
		
		if(stringParamList != null) {
			List<String> stringParamListFinal = stringParamList;
			Specification<Veicolo> stringParamSpecification = Specification.where((root, query, criteriaBuilder) -> 
				root.get(fieldName).in(stringParamListFinal));
			filterSpec.add(stringParamSpecification);
		}
	}
	

}

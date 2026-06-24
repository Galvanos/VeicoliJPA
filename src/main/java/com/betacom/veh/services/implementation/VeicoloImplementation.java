package com.betacom.veh.services.implementation;

import com.betacom.veh.controllers.exceptionhandler.ExceptionManager;
import com.betacom.veh.repositories.IVeicoloRepository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.betacom.veh.services.interfaces.IVeicoloService;
import com.betacom.veh.utils.Utils;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

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
		
		addMinMaxIntParamToSpecificationList(filterSpec, filter.getMinAnnoProduzione(), filter.getMaxAnnoProduzione(), "annoProduzione");
		
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
		
		List<Specification<Veicolo>> minMaxCc = new LinkedList<>();
		
		addMinMaxIntParamToSpecificationListAutomobile(minMaxCc, filter.getMinCc(), filter.getMaxCc(), "cc");
		
		addMinMaxIntParamToSpecificationListMoto(minMaxCc, filter.getMinCc(), filter.getMaxCc(), "cc");
		
		Specification<Veicolo> minMaxCcSpecification = Specification.anyOf(minMaxCc);
		
		filterSpec.add(minMaxCcSpecification);
		
		List<String> coloreList = filter.getColore();
		
		addStringParamCaseInsensitiveLikeToSpecificationList(filterSpec, coloreList, "colore");
		
		List<Integer> idList = filter.getId();
		
		addIntegerParamToSpecificationList(filterSpec, idList, "id");
		
		List<String> marcaList = filter.getMarca();
		
		addStringParamCaseInsensitiveLikeToSpecificationList(filterSpec, marcaList, "marca");
		
		List<String> modelloList = filter.getModello();
		
		addStringParamCaseInsensitiveLikeToSpecificationList(filterSpec, modelloList, "modello");
		
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
		
		addMinMaxIntParamToSpecificationListAutomobile(filterSpec, filter.getMinNumeroPorte(), filter.getMaxNumeroPorte(), "numeroPorte");
		
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
		
		addMinMaxIntParamToSpecificationList(filterSpec, filter.getMinNumeroPorte(), filter.getMaxNumeroRuote(), "numeroRuote");

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
				List<Specification<Automobile>> targaSpecList = targaListFinal.stream().map(targa -> createLikeSpecificationAutomobile("targa", targa)).toList();
				Specification<Automobile> anyTargheTest = Specification.anyOf(targaSpecList);
				return anyTargheTest.toPredicate(automobileRoot, query, criteriaBuilder);
			});
			Specification<Veicolo> targaMotoSpecification = Specification.where((root, query, criteriaBuilder) -> {
				Root<Moto> motoRoot = criteriaBuilder.treat(root, Moto.class);
				List<Specification<Moto>> targaSpecList = targaListFinal.stream().map(targa -> createLikeSpecificationMoto("targa", targa)).toList();
				Specification<Moto> anyTargheTest = Specification.anyOf(targaSpecList);
				return anyTargheTest.toPredicate(motoRoot, query, criteriaBuilder);
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
	
	private Specification<Automobile> createLikeSpecificationAutomobile(String fieldName, String param) {
		return createLikeSpecification(fieldName, param);
	}

	private Specification<Moto> createLikeSpecificationMoto(String fieldName, String param) {
		return createLikeSpecification(fieldName, param);
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
	
	private void addStringParamCaseInsensitiveLikeToSpecificationList(List<Specification<Veicolo>> filterSpec,
			List<String> stringParamList, String fieldName) {
		stringParamList = Optional.ofNullable(stringParamList).map(Utils::removeNullsAndBlanks).map(t -> t.isEmpty()?null:t).orElse(null);
		if (stringParamList != null) {
			List<Specification<Veicolo>> listLike=stringParamList.stream().map(t -> createLikeSpecificationVeicolo(fieldName, t)).toList();
			Specification<Veicolo> allLike = Specification.anyOf(listLike);
			filterSpec.add(allLike);
		}
	}

	private Specification<Veicolo> createLikeSpecificationVeicolo(String fieldName, String param) {
		return createLikeSpecification(fieldName, param);
	}
	
	private <T> Specification<T> createLikeSpecification(String fieldName, String param){
		return Specification.where((root, query, criteriaBuilder) -> {
			String likePattern = param.toUpperCase();
			likePattern = "%" + likePattern + "%";
			return criteriaBuilder.like(criteriaBuilder.upper(root.get(fieldName)), likePattern);
		});
	}
	
	private void addMinMaxIntParamToSpecificationList(List<Specification<Veicolo>> filterSpec,Integer min,Integer max,String fieldName) {
		Specification<Veicolo> minSpec = Optional.ofNullable(min).map(t -> createMinSpecification(t, fieldName)).orElse(null);
		Specification<Veicolo> maxSpec = Optional.ofNullable(max).map(t -> createMaxSpecification(t, fieldName)).orElse(null);
		List<Specification<Veicolo>> minMaxSpecsList = new LinkedList<>();
		minMaxSpecsList.add(minSpec);
		minMaxSpecsList.add(maxSpec);
		minMaxSpecsList.removeIf(Objects::isNull);
		Specification<Veicolo> minMaxSpec = Specification.allOf(minMaxSpecsList);
		filterSpec.add(minMaxSpec);
	}

	private Specification<Veicolo> createMinSpecification(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get(fieldName), min));
	}
	
	private Specification<Veicolo> createMaxSpecification(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get(fieldName), min));
	}
	
	
	private void addMinMaxIntParamToSpecificationListMoto(List<Specification<Veicolo>> filterSpec, Integer min,
			Integer max, String fieldName) {
		Specification<Veicolo> minSpec = Optional.ofNullable(min).map(t -> createMinSpecificationMoto(t, fieldName))
				.orElse(null);
		Specification<Veicolo> maxSpec = Optional.ofNullable(max).map(t -> createMaxSpecificationMoto(t, fieldName))
				.orElse(null);
		List<Specification<Veicolo>> minMaxSpecsList = new LinkedList<>();
		minMaxSpecsList.add(minSpec);
		minMaxSpecsList.add(maxSpec);
		minMaxSpecsList.removeIf(Objects::isNull);
		Specification<Veicolo> minMaxSpec = Specification.allOf(minMaxSpecsList);
		filterSpec.add(minMaxSpec);
	}

	private Specification<Veicolo> createMinSpecificationMoto(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Moto> rootMoto = criteriaBuilder.treat(root, Moto.class);
			return criteriaBuilder.greaterThanOrEqualTo(rootMoto.get(fieldName), min);
		});
	}

	private Specification<Veicolo> createMaxSpecificationMoto(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Moto> rootMoto = criteriaBuilder.treat(root, Moto.class);
			return criteriaBuilder.lessThanOrEqualTo(rootMoto.get(fieldName), min);
		});
	}

	private void addMinMaxIntParamToSpecificationListAutomobile(List<Specification<Veicolo>> filterSpec, Integer min,
			Integer max, String fieldName) {
		Specification<Veicolo> minSpec = Optional.ofNullable(min)
				.map(t -> createMinSpecificationAutomobile(t, fieldName)).orElse(null);
		Specification<Veicolo> maxSpec = Optional.ofNullable(max)
				.map(t -> createMaxSpecificationAutomobile(t, fieldName)).orElse(null);
		List<Specification<Veicolo>> minMaxSpecsList = new LinkedList<>();
		minMaxSpecsList.add(minSpec);
		minMaxSpecsList.add(maxSpec);
		minMaxSpecsList.removeIf(Objects::isNull);
		Specification<Veicolo> minMaxSpec = Specification.allOf(minMaxSpecsList);
		filterSpec.add(minMaxSpec);
		
		
	}

	private Specification<Veicolo> createMinSpecificationAutomobile(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Automobile> rootAutomobile = criteriaBuilder.treat(root, Automobile.class);
			return criteriaBuilder.greaterThanOrEqualTo(rootAutomobile.get(fieldName), min);
		});
	}

	private Specification<Veicolo> createMaxSpecificationAutomobile(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Automobile> rootAutomobile = criteriaBuilder.treat(root, Automobile.class);
			return criteriaBuilder.lessThanOrEqualTo(rootAutomobile.get(fieldName), min);
		});
	}

	private void addMinMaxIntParamToSpecificationListBici(List<Specification<Veicolo>> filterSpec, Integer min,
			Integer max, String fieldName) {
		Specification<Veicolo> minSpec = Optional.ofNullable(min).map(t -> createMinSpecificationBici(t, fieldName))
				.orElse(null);
		Specification<Veicolo> maxSpec = Optional.ofNullable(max).map(t -> createMaxSpecificationBici(t, fieldName))
				.orElse(null);
		List<Specification<Veicolo>> minMaxSpecsList = new LinkedList<>();
		minMaxSpecsList.add(minSpec);
		minMaxSpecsList.add(maxSpec);
		minMaxSpecsList.removeIf(Objects::isNull);
		Specification<Veicolo> minMaxSpec = Specification.allOf(minMaxSpecsList);
		filterSpec.add(minMaxSpec);
	}

	private Specification<Veicolo> createMinSpecificationBici(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Bici> rootBici = criteriaBuilder.treat(root, Bici.class);
			return criteriaBuilder.greaterThanOrEqualTo(rootBici.get(fieldName), min);
		});
	}

	private Specification<Veicolo> createMaxSpecificationBici(Integer min, String fieldName) {
		return Specification.where((root, query, criteriaBuilder) -> {
			Root<Bici> rootBici = criteriaBuilder.treat(root, Bici.class);
			return criteriaBuilder.lessThanOrEqualTo(rootBici.get(fieldName), min);
		});
	}
}

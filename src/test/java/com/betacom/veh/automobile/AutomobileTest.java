package com.betacom.veh.automobile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.output.AutomobileDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutomobileTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ITipoAlimentazioneRepository typeRepo;
	
	
	private AutomobileRequest createValidAutomobileCreateRequest(String targa) {
		return AutomobileRequest.builder(
				).annoProduzione(2020)
				.categoria("BERLINA_MEDIA")
				.cc(300)
				.colore("giallo")
				.marca("Nissan")
				.modello("micra")
				.targa(targa)
				.tipoAlimentazione("BENZINA")
				.numeroRuote(4)
				.numeroPorte(5)
				.build();
	}
	private AutomobileDTO insertAutomobile(String targa) throws Exception{
		AutomobileRequest request = createValidAutomobileCreateRequest(targa);
		MvcResult result = mockMvc.perform(post("/rest/automobile/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk())
					.andReturn();
		return objectMapper.readValue(result.getResponse().getContentAsString(), AutomobileDTO.class);
	}	
	
	private MvcResult getPostErrorResult(String uri, AutomobileRequest request) throws Exception{
		return mockMvc.perform(post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				).andExpect(status().isBadRequest())
				.andReturn();
	}
	
	@Test
	@Order(1)
	public void createAutomobileTest() throws Exception{
		TipoAlimentazione tipo = new TipoAlimentazione();								//creando tipo alimentazione
		tipo.setTipoAlimentazioneId(TipoAlimentazioneId.builder()						//creando tipo alimentazione
				.tipoAlimentazione("BENZINA").tipoVeicolo("AUTOMOBILE").build());		//creando tipo alimentazione
		typeRepo.save(tipo);															//creando tipo alimentazione
		AutomobileRequest request = createValidAutomobileCreateRequest("SS667BV");		
		try {
			mockMvc.perform(post("/rest/automobile/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk())
					.andReturn();

		} catch (Exception e) {
			log.error("Error in create: " + e.getMessage());
		}		
	}
	
	@Test
	@Order(2)
	public void createAutomobileErrorTest() throws Exception{
		AutomobileRequest request = createValidAutomobileCreateRequest("RRRRRRRRRR");	//test targa formato errato
		MvcResult result = getPostErrorResult("/rest/automobile/create", request);			
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'targa invalida'");		
		request.setTarga("RT789FV");		//reset valore
		
		request.setAnnoProduzione(1992);												//test anno fuori range
		result = getPostErrorResult("/rest/automobile/create", request);
				
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("error: " + dto.getMsg() + ", expected: " + "'anno invalido'");
		request.setAnnoProduzione(LocalDateTime.now().getYear()+1);						//test anno nel futuro
		result = getPostErrorResult("/rest/automobile/create", request);
				
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("error: " + dto.getMsg() + ", expected: " + "'anno invalido'");		
		request.setAnnoProduzione(LocalDateTime.now().getYear());	//reset valore
		
		request.setTarga("SS667BV");													//test targa duplicata
		result = getPostErrorResult("/rest/automobile/create", request);			
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'targa già presente'");
		request.setTarga("RT789FV");		//reset valore
		
		request.setCategoria("TORO_SEDUTO");											//test categoria non esistente
		result = getPostErrorResult("/rest/automobile/create", request);			
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'categoria non valida'");
		request.setCategoria("BERLINA_MEDIA");	//reset valore
		
		request.setTipoAlimentazione("STRACCHINO");											//test tipo alimentazione non esistente
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'tipo alimentazione non valido'");
		request.setTipoAlimentazione("BENZINA");	//reset valore
		
		request.setNumeroRuote(-1);											//test numero ruote negativo
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'numero ruote non valido'");
		request.setNumeroRuote(580);											//test numero ruote fuori scala
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'numero ruote non valido'");
		request.setNumeroRuote(4);		//reset valore
		
		request.setNumeroPorte(333);											//test numero porte fuori scala
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'numero porte non valido'");
		request.setNumeroPorte(-4);											//test numero porte negativo
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);

		log.debug("error: " + dto.getMsg() + ", expected: " + "'numero porte non valido'");
	}
	
	@Test
	@Order(3)
	public void updateAutomobileTestSameTarga() throws Exception{
		AutomobileDTO insertedCar = insertAutomobile("VV333FF");
		updateAutomobile(insertedCar.getTarga(), insertedCar.getId());
	}
	
	@Test
	@Order(4)
	public void updateAutomobileTestDifferentTarga() throws Exception{
		AutomobileDTO insertedCar = insertAutomobile("XX333FF");
		updateAutomobile("FF999NW", insertedCar.getId());
	}
	
	@Test
	@Order(5)
	public void updateAutomobileTestError() throws Exception{
		AutomobileRequest request = AutomobileRequest.builder().id(99999999).cc(250).targa("RF444GB").build();
		MvcResult result = mockMvc.perform(patch("/rest/automobile/update")				
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				).andExpect(status().isBadRequest())
				.andReturn();

		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("update result: " + dto);
	}
	
	private void updateAutomobile(String targa, Integer id) throws Exception{
		AutomobileRequest request = AutomobileRequest.builder().id(id).cc(250).targa(targa).build();
		try {
			MvcResult result = mockMvc.perform(patch("/rest/automobile/update")				
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk())
					.andReturn();

			String json = result.getResponse().getContentAsString();
			ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
			
			log.debug("update result: " + dto);
		}catch (Exception e) {
			log.debug("Errore:" + e.getMessage());
		}
	}
	
	@Test
	@Order(6)
	public void deleteAutomobileTest() throws Exception{
		AutomobileDTO insertedCar = insertAutomobile("DD333FF");
		mockMvc.perform(delete("/rest/automobile/delete/" + insertedCar.getId()))
        	.andExpect(status().isOk());
		log.debug("Auto deleted");
	}
	
	@Test
	@Order(7)
	public void deleteAutomobileTestError() throws Exception{
		mockMvc.perform(delete("/rest/automobile/delete/" + 999999999))
        	.andExpect(status().isBadRequest());
		log.debug("Auto deleted");
	}
	
	@Test
	@Order(8)
	public void getAutomobileByIdTest() throws Exception{
		AutomobileDTO insertedCar = insertAutomobile("HH223FF");
		MvcResult result = mockMvc.perform(get("/rest/automobile/getById").param("id", insertedCar.getId().toString()))
			.andExpect(status().isOk())
			.andReturn();
		
		String json = result.getResponse().getContentAsString();
		insertedCar = objectMapper.readValue(json, AutomobileDTO.class);		
		log.debug(insertedCar.toString());
	}
	
	@Test
	@Order(9)
	public void getAutomobileByIdTestError() throws Exception{
		MvcResult result = mockMvc.perform(get("/rest/automobile/getById").param("id", "99999999"))
			.andExpect(status().isBadRequest())
			.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO errorMessage = objectMapper.readValue(json, ResponseDTO.class);		
		log.debug("" + errorMessage.getMsg());
	}
	
	@Test
	@Order(10)
	public void getAllAutomobile() throws Exception{
		MvcResult result = mockMvc.perform(get("/rest/automobile/list"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		
		List<AutomobileDTO> lS = objectMapper.readValue(
	            json,
	            new TypeReference<List<AutomobileDTO>>() {}
	    );
		
		assertFalse(lS.isEmpty());
		lS.forEach(s -> log.debug(s.toString()));
	}
}

package com.betacom.veh.automobile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.assertj.core.util.Arrays;
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
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.models.Automobile;
import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;

import lombok.extern.slf4j.Slf4j;
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
	
	
	
	private Automobile insertedCar;
	
	private AutomobileRequest createValidAutomobileCreateRequest() {
		return AutomobileRequest.builder(
				).annoProduzione(2020)
				.categoria("BERLINA_MEDIA")
				.cc(300)
				.colore("giallo")
				.marca("Nissan")
				.modello("micra")
				.targa("RR456GG")
				.tipoAlimentazione("BENZINA")
				.numeroRuote(4)
				.numeroPorte(5)
				.build();
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
		AutomobileRequest request = createValidAutomobileCreateRequest();		
		try {
			MvcResult result = mockMvc.perform(post("/rest/automobile/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk())
					.andReturn();
			insertedCar = objectMapper.readValue(result.getResponse().getContentAsString(), Automobile.class);
		} catch (Exception e) {
			log.error("Error in create: " + e.getMessage());
		}		
	}
	
	@Test
	@Order(2)
	public void createAutomobileErrorTest() throws Exception{
		AutomobileRequest request = createValidAutomobileCreateRequest();
		request.setAnnoProduzione(1992);
		MvcResult result = getPostErrorResult("/rest/automobile/create", request);
				
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("error: " + dto.getMsg() + ", expected: " + "'anno invalido'");
		
		request.setAnnoProduzione(LocalDateTime.now().getYear());
		request.setTarga("RRRRRRRRRR");
		
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);
		

		log.debug("error: " + dto.getMsg() + ", expected: " + "'targa invalida'");
	}
	
	@Test
	@Order(3)
	public void updateAutomobileTest() throws Exception{
		AutomobileRequest request = AutomobileRequest.builder().id(insertedCar.getId()).cc(250).targa(insertedCar.getTarga()).build();
		MvcResult result = mockMvc.perform(post("/rest/automobile/update")				
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request))
				).andExpect(status().isOk())
				.andReturn();
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("update result: " + dto);
	}
	
	@Test
	@Order(4)
	public void deleteAutomobileTest() throws Exception{

		mockMvc.perform(delete("/rest/automobile/delete/" + insertedCar.getId()))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.msg").exists());
		
		log.debug("Auto deleted");
	}
}

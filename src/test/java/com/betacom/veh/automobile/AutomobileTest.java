package com.betacom.veh.automobile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Date;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.output.ResponseDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
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
	
	private AutomobileRequest createValidAutomobileRequest() {
		return AutomobileRequest.builder(
				).annoProduzione(2020)
				.categoria("AUTOMOBILE")
				.cc(300)
				.colore("giallo")
				.marca("Nissan")
				.modello("micra")
				.targa("RR456GG")
				.tipoAlimentazione("ELETTRICA")
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
		AutomobileRequest request = createValidAutomobileRequest();		
		try {
			mockMvc.perform(post("/rest/automobile/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk());
		} catch (Exception e) {
			log.error("Error in create: " + e.getMessage());
		}		
	}
	
	@Test
	@Order(2)
	public void createAutomobileErrorTest() throws Exception{
		AutomobileRequest request = createValidAutomobileRequest();
		request.setAnnoProduzione(1992);
		MvcResult result = getPostErrorResult("/rest/automobile/create", request);
				
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("error: " + dto.getMsg() + ", expected: " + "'invalid year'");
		
		request.setAnnoProduzione(LocalDateTime.now().getYear());
		request.setTarga("RRRRRRRRRR");
		
		result = getPostErrorResult("/rest/automobile/create", request);
		
		json = result.getResponse().getContentAsString();
		dto = objectMapper.readValue(json, ResponseDTO.class);
	}
}

package com.betacom.veh.veicolo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;

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

import com.betacom.veh.dto.output.VeicoloDTO;
import tools.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestVeicoloController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void testListVeicoliConFiltri() throws Exception {
		MvcResult result = mockMvc.perform(get("/rest/veicolo/list")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String contentString = result.getResponse().getContentAsString();
		List<Map<String, Object>> veicoli = objectMapper.readValue(contentString, new TypeReference<List<Map<String, Object>>>() {});
		
		log.info("Found {} vehicles in general search", veicoli.size());
		assertNotNull(veicoli);
	}
	
	@Test
	@Order(2)
	public void testGetVeicoloById() throws Exception {
		MvcResult result = mockMvc.perform(get("/rest/veicolo/getById")
				.param("id", "1")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
				
		String contentString = result.getResponse().getContentAsString();
		Map<String, Object> veicolo = objectMapper.readValue(
				contentString, 
				new TypeReference<java.util.Map<String, Object>>() {}
			);
		
		assertNotNull(veicolo);
		Object vehicleId = veicolo.get("id");
		log.info("Successfully retrieved vehicle by ID: {}", vehicleId);
	}
}

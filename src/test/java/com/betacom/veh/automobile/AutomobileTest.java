package com.betacom.veh.automobile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.betacom.veh.dto.input.AutomobileRequest;

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
	
	@Test
	@Order(1)
	public void createAutomobileTest() throws Exception{
		AutomobileRequest request = AutomobileRequest.builder(
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
		
		try {
			mockMvc.perform(post("/rest/automobile/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request))
					).andExpect(status().isOk());
		} catch (Exception e) {
			log.error("Error in create: " + e.getMessage());
		}
		
	}
}

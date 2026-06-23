package com.betacom.veh.categorie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.net.ssl.SSLEngineResult.Status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.betacom.veh.dto.input.CategoriaRequest;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCategorieController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void createCategories() throws Exception {
		CategoriaRequest categoriaAutomobile = CategoriaRequest.builder()
				.categoria("BERLINA_MEDIA")
				.tipoVeicolo("AUTOMOBILE").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		CategoriaRequest categoriaBicicletta = CategoriaRequest.builder()
				.categoria("MOUNTAIN")
				.tipoVeicolo("BICICLETTA").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaBicicletta))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		CategoriaRequest categoriaMotoveicolo = CategoriaRequest.builder()
				.categoria("MOUNTAIN")
				.tipoVeicolo("MOTOVEICOLO").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaMotoveicolo))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
}

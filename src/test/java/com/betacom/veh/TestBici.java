package com.betacom.veh;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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

import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.exceptions.AcademyException;
import com.betacom.veh.models.TipoFreno;
import com.betacom.veh.models.TipoSospensione;
import com.betacom.veh.repositories.IBiciRepository;
import com.betacom.veh.repositories.IFrenoRepository;
import com.betacom.veh.repositories.ISospensioneRepository;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestBici {
	
	@Autowired
	private IFrenoRepository frenR;
	@Autowired
	private ISospensioneRepository sospR;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	@Order(1)
	public void setUpFrenoSospensione() throws Exception{
		
		List<String> listaFreno = List.of("DISCO","TAMPONE","CONTROPEDALATA");
		List<String> listaSospensione = List.of("ANTERIORE","POSTERIORE","MISTA");
		
		TipoFreno freno = new TipoFreno();
		
		for(String lF: listaFreno) {
			freno.setTipoFreno(lF);
			try {
				frenR.save(freno);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		TipoSospensione sospensione = new TipoSospensione();
		
		for(String lS: listaSospensione) {
			sospensione.setTipoSospensione(lS);
			try {
				sospR.save(sospensione);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	@Order(2)
	public void createTestBici() throws Exception{
		BiciRequest req = BiciRequest.builder()
				.numeroRapporti(10)
				.tipoFreno("DISCO")
				.tipoSospensione("ANTERIORE")
				.pieghevole(false)
				.build();
		
		mockMvc.perform(post("/rest/bici/create")
			.contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(req))
			).andExpect(status().isOk());
	}
	
	@Test
	@Order(3)
	public void createTestBiciError() throws Exception{
		BiciRequest req = BiciRequest.builder()
				.numeroRapporti(1000)
				.tipoFreno("ROTTO")
				.tipoSospensione("SUSPANCE")
				.pieghevole(false)
				.build();
		MvcResult result = mockMvc.perform(post("/rest/bici/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isBadRequest())
		.andReturn();
		
		String json = result.getRequest().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
	}
	
	@Test
	@Order(4)
	public void upateTestBici() throws Exception{
		BiciRequest req = BiciRequest.builder()
				.id(1)
				.numeroRapporti(27)
				.build();
		MvcResult result = mockMvc.perform(put("/rest/bici/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isOk())
		.andReturn();
		
		String json = result.getRequest().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
	}
	
	@Test
	@Order(5)
	public void upateTestBiciError() throws Exception{
		BiciRequest req = BiciRequest.builder()
				.numeroRapporti(1000)
				.tipoFreno("ROTTO")
				.tipoSospensione("SUSPANCE")
				.pieghevole(false)
				.build();
		MvcResult result = mockMvc.perform(put("/rest/bici/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req))
				)
		.andExpect(status().isOk())
		.andReturn();
		
		String json = result.getRequest().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
	}
}

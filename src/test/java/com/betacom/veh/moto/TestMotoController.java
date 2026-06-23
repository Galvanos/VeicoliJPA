package com.betacom.veh.moto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Commit;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.mapping.MotoMap;
import com.betacom.veh.dto.output.MotoDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.models.Categoria;
import com.betacom.veh.models.CategoriaId;
import com.betacom.veh.models.Moto;
import com.betacom.veh.models.TipoAlimentazione;
import com.betacom.veh.models.TipoAlimentazioneId;
import com.betacom.veh.repositories.ICategoriaRepository;
import com.betacom.veh.repositories.IMotoRepository;
import com.betacom.veh.repositories.ITipoAlimentazioneRepository;
import com.betacom.veh.utils.GeneraTargaMoto;

import jakarta.transaction.Transactional;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@org.springframework.transaction.annotation.Transactional
public class TestMotoController {
	
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ITipoAlimentazioneRepository aliRepo;
	@Autowired
	private IMotoRepository motoRepo;
	
	private GeneraTargaMoto targa = new GeneraTargaMoto();
	
	@BeforeAll
    public static void setup(
    		@Autowired IMotoRepository motoRepo, 
    		@Autowired ITipoAlimentazioneRepository aliRepo) {
        motoRepo.deleteAll();
        aliRepo.deleteAll();
    }
	
	@Test
	@Order(1)
	public void createMotoTest() throws Exception{
		log.debug("createMotoTest");
		
		if (!aliRepo.existsById(new TipoAlimentazioneId("MOTOVEICOLO","BENZINA"))) {
	        aliRepo.saveAndFlush(new TipoAlimentazione(new TipoAlimentazioneId("MOTOVEICOLO","BENZINA")));
	    }
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CD")
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		mockMvc.perform(post("/rest/moto/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(2)
	public void createMotoTargaNullErrorTest() throws Exception{
		log.debug("createMotoTargaNullErrorTest");
		
		MotoRequest req = MotoRequest.builder()
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	@Test
	@Order(3)
	public void createMotoCcErrorTest() throws Exception{
		log.debug("createMotoCcErrorTest");
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CD")
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	@Test
	@Order(4)
	public void createMotoTargaDuplicateErrorTest() throws Exception{
		log.debug("createMotoTargaDuplicateErrorTest");
		
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CD")
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		mockMvc.perform(post("/rest/moto/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isBadRequest());
	}
	
	

	@Test
	@Order(5)
	public void createMotoRuoteErrorTest() throws Exception{
		log.debug("createMotoRuoteErrorTest");
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CE")
                .cc(600)
                .numeroRuote(100)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	@Test
	@Order(6)
	public void createMotoAnnoErrorTest() throws Exception{
		log.debug("createMotoAnnoErrorTest");
		
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CF")
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(1925)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	@Test
	@Order(7)
	public void createMotoCategoriaErrorTest() throws Exception{
		log.debug("createMotoCategoriaErrorTest");
		
		MotoRequest req = MotoRequest.builder()
                .targa("AB123CG")
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("BENZINA")
                .categoria("NONVALIDA")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	@Test
	@Order(8)
	public void createMotoAlimentazioneErrorTest() throws Exception{
		log.debug("createMotoAlimentazioneErrorTest");
		
		MotoRequest req = MotoRequest.builder()
                .targa("AB123Ch")
                .cc(600)
                .numeroRuote(2)
                .tipoAlimentazione("NONVALIDA")
                .categoria("MOUNTAIN")
                .colore("ROSSO")
                .marca("Ducati")
                .annoProduzione(2025)
                .modello("Panigale")
                .build();
		
		MvcResult result = mockMvc.perform(post("/rest/moto/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isBadRequest())		 
				.andReturn();
		
		String json = result.getResponse().getContentAsString();
		ResponseDTO dto = objectMapper.readValue(json, ResponseDTO.class);
		
		log.debug("rc create :{}", dto.getMsg());
	}
	
	
	@Test
	@Order(9)
	public void createSecondaMotoTest() throws Exception {
	    log.debug("createSecondaMotoTest");
	    
	    MotoRequest req = MotoRequest.builder()
	            .targa("EE999FF")
	            .cc(1200)
	            .numeroRuote(2)
	            .tipoAlimentazione("BENZINA")
	            .categoria("MOUNTAIN")
	            .colore("BLU")
	            .marca("Yamaha")
	            .annoProduzione(2026)
	            .modello("R1")
	            .build();
	    
	    mockMvc.perform(post("/rest/moto/create")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(req)))
	            .andExpect(status().isOk());
	}
	
	@Test
	@Order(10)
	public void listMotoTest() throws Exception{
		log.debug("listMotoTest");
		
		MvcResult result = mockMvc.perform(get("/rest/moto/list"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		List<MotoDTO> lM = objectMapper.readValue(json,
	            new TypeReference<List<MotoDTO>>() {}
	    );
		
		assertFalse(lM.isEmpty());
		lM.forEach(m -> log.debug(m.toString()));
	}
	
	
	@Test
	@Order(11)
	public void getByIdMotoTest() throws Exception{
		log.debug("getByIdAttivita");
		
		MvcResult result = mockMvc.perform(get("/rest/moto/getById").param("id", "1"))
	            .andExpect(status().isOk())
	            .andReturn();
		  
		String json = result.getResponse().getContentAsString();
		MotoDTO mo = objectMapper.readValue(json,MotoDTO.class);
		
		log.debug(mo.toString());
	} 

	
	@Test
	@Order (12)
	public void updateMotoTest() throws Exception {
		log.debug("updateMotoTest");
		MotoRequest req = MotoRequest.builder()
		.id(1)
		.cc(1000)
		.colore("NERO")
		.build();
			
		mockMvc.perform(put("/rest/moto/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(req)))
				.andExpect(status().isOk());
	}
	
	@Test
	@Order(13)
	public void deleteMotoTest() throws Exception{
		log.debug("deleteMoto");
		
		mockMvc.perform(MockMvcRequestBuilders.delete("/rest/moto/delete/1"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$.msg").exists());
		  
	}
}

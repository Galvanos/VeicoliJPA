package com.betacom.veh.alimentazione;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.assertj.core.api.Assertions;
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

import com.betacom.veh.dto.input.TipoAlimentazioneRequest;
import com.betacom.veh.dto.output.TipoAlimentazioneDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTipoAlimentazioneController {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * Test percorso di creazione normale delle alimentazioni
	 * @throws Exception
	 */
	@Test
	@Order(1)
	public void createAlimentazioni() throws Exception {
		TipoAlimentazioneRequest alimentazioneAutomobile = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("DIESEL")
				.tipoVeicolo("AUTOMOBILE").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(alimentazioneAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		TipoAlimentazioneRequest categoriaBicicletta = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("MANUALE")
				.tipoVeicolo("BICICLETTA").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaBicicletta))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		TipoAlimentazioneRequest categoriaMotoveicolo = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("BENZINA")
				.tipoVeicolo("MOTOVEICOLO").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaMotoveicolo))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	/**
	 * Test tentando di inserire un tipo di veicolo inesistente
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void createPoweredInvalidVehicleType() throws Exception {
		//anche se in futuro verranno inseriti altri tipi di veicolo (es. treno), è estremamente improbabile che sia un caseggiato...
		TipoAlimentazioneRequest categoriaAutomobile = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("FOTOVOLTAICO")
				.tipoVeicolo("CASEGGIATO").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	/**
	 * Test atto a verificare la list realizzata
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void listAlimentazioni() throws Exception {
		
		MvcResult listResult = mockMvc.perform(get("/rest/alimentazione/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<TipoAlimentazioneDTO> contentTipoAlimentazioneDTOList = objectMapper.readValue(contentString, new TypeReference<List<TipoAlimentazioneDTO>>() {
		});
		
		assertFalse(contentTipoAlimentazioneDTOList.isEmpty());
		
		assertEquals(3,contentTipoAlimentazioneDTOList.size());
		
		Assertions.assertThat(contentTipoAlimentazioneDTOList).containsExactlyInAnyOrder(new TipoAlimentazioneDTO("AUTOMOBILE","DIESEL"),
				new TipoAlimentazioneDTO("BICICLETTA","MANUALE"),new TipoAlimentazioneDTO("MOTOVEICOLO","BENZINA"));
		
	}
	
	/**
	 * Test atto a verificare la normalizzazione del nome della categoria, vale a dire il trim, 
	 * la conversione in maiuscolo, la rimozione degli spazi duplicati e la sostituzione degli spazi con _
	 */
	@Test
	@Order(4)
	public void testAlimentazioneNormalization() throws Exception {
		
		TipoAlimentazioneRequest categoriaAutomobile = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("     miscela      80    ")
				.tipoVeicolo("MOTOVEICOLO").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		//recupero la list delle categorie per recuperare la categoria normalizzata
		MvcResult listResult = mockMvc.perform(get("/rest/alimentazione/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<TipoAlimentazioneDTO> contentTipoAlimentazioneDTOList = objectMapper.readValue(contentString, new TypeReference<List<TipoAlimentazioneDTO>>() {
		});
		
		assertFalse(contentTipoAlimentazioneDTOList.isEmpty());
		
		Assertions.assertThat(contentTipoAlimentazioneDTOList).contains(new TipoAlimentazioneDTO("MOTOVEICOLO", "MISCELA_80"));
		
		
	}
	
	/**
	 * Test atto a verificare se il tipo di veicolo viene convertito in maiuscolo e sottoposto a trim
	 * @throws Exception
	 */
	@Test
	@Order(5)
	public void testTipoVeicoloNormalization() throws Exception {
		
		TipoAlimentazioneRequest categoriaAutomobile = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("IBRIDA")
				.tipoVeicolo("     automobile     ").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		//recupero la list delle categorie per recuperare la categoria normalizzata
		MvcResult listResult = mockMvc.perform(get("/rest/alimentazione/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<TipoAlimentazioneDTO> contentTipoAlimentazioneDTOList = objectMapper.readValue(contentString, new TypeReference<List<TipoAlimentazioneDTO>>() {
		});
		
		assertFalse(contentTipoAlimentazioneDTOList.isEmpty());
		
		Assertions.assertThat(contentTipoAlimentazioneDTOList).contains(new TipoAlimentazioneDTO("AUTOMOBILE", "IBRIDA"));
		
		
	}
	
	@Test
	@Order(6)
	public void testNoTipoAlimentazione() throws Exception{
		TipoAlimentazioneRequest categoriaAutomobile = TipoAlimentazioneRequest.builder()
				.tipoVeicolo("AUTOMOBILE").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(7)
	public void testNoTipoVeicolo() throws Exception{
		TipoAlimentazioneRequest categoriaNoTipo = TipoAlimentazioneRequest.builder()
				.tipoAlimentazione("SOLARE").build();
		mockMvc.perform(post("/rest/alimentazione/create")
				.content(objectMapper.writeValueAsString(categoriaNoTipo))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(8)
	public void testDelete() throws Exception{
		
		mockMvc.perform(delete("/rest/alimentazione/delete/{tipoVeicolo}/{categoria}","AUTOMOBILE","IBRIDA")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		MvcResult listResult = mockMvc.perform(get("/rest/alimentazione/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<TipoAlimentazioneDTO> contentTipoAlimentazioneDTOList = objectMapper.readValue(contentString, new TypeReference<List<TipoAlimentazioneDTO>>() {
		});
		
		assertFalse(contentTipoAlimentazioneDTOList.isEmpty());
		
		assertFalse(contentTipoAlimentazioneDTOList.contains(new TipoAlimentazioneDTO("AUTOMOBILE", "IBRIDA")));
	}
	
	/**
	 * Cancella una categiria non esistente, per il fatto che i test sono in ordine,
	 *  cancello di nuovo la categoria cancellata nel test precedente
	 * @throws Exception
	 */
	@Test
	@Order(9)
	public void testDeleteNotExisting() throws Exception{
		
		mockMvc.perform(delete("/rest/alimentazione/delete/{tipoVeicolo}/{categoria}","AUTOMOBILE","IBRIDA")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		
	}
	
}

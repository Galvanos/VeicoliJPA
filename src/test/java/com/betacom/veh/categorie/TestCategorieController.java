package com.betacom.veh.categorie;

import static org.assertj.core.api.Assertions.fail;
import static org.hamcrest.CoreMatchers.is;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.betacom.veh.dto.input.CategoriaRequest;
import com.betacom.veh.dto.output.CategoriaDTO;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.type.TypeReference;
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
	
	/**
	 * Test percorso di creazione normale delle categorie
	 * @throws Exception
	 */
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
	
	/**
	 * Test tentando di inserire una categoria di veicolo inesistente
	 * @throws Exception
	 */
	@Test
	@Order(2)
	public void createCategoryInvalidVehicleType() throws Exception {
		//anche se in futuro verranno inseriti altri tipi di veicolo (es. treno), è estremamente improbabile che sia un caseggiato...
		CategoriaRequest categoriaAutomobile = CategoriaRequest.builder()
				.categoria("GRATTACIELO")
				.tipoVeicolo("CASEGGIATO").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	/**
	 * Test atto a verificare la list realizzata
	 * @throws Exception
	 */
	@Test
	@Order(3)
	public void listCategories() throws Exception {
		
		MvcResult listResult = mockMvc.perform(get("/rest/categoria/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<CategoriaDTO> contentCategoriaDTOList = objectMapper.readValue(contentString, new TypeReference<List<CategoriaDTO>>() {
		});
		
		assertFalse(contentCategoriaDTOList.isEmpty());
		
		assertEquals(3,contentCategoriaDTOList.size());
		
		Assertions.assertThat(contentCategoriaDTOList).containsExactlyInAnyOrder(new CategoriaDTO("AUTOMOBILE","BERLINA_MEDIA"),
				new CategoriaDTO("BICICLETTA","MOUNTAIN"),new CategoriaDTO("MOTOVEICOLO","MOUNTAIN"));
		
	}
	
	/**
	 * Test atto a verificare la normalizzazione del nome della categoria, vale a dire il trim, 
	 * la conversione in maiuscolo, la rimozione degli spazi duplicati e la sostituzione degli spazi con _
	 */
	@Test
	@Order(4)
	public void testCategoryNormalization() throws Exception {
		
		CategoriaRequest categoriaAutomobile = CategoriaRequest.builder()
				.categoria("     berlina      compatta    ")
				.tipoVeicolo("AUTOMOBILE").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		//recupero la list delle categorie per recuperare la categoria normalizzata
		MvcResult listResult = mockMvc.perform(get("/rest/categoria/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<CategoriaDTO> contentCategoriaDTOList = objectMapper.readValue(contentString, new TypeReference<List<CategoriaDTO>>() {
		});
		
		assertFalse(contentCategoriaDTOList.isEmpty());
		
		Assertions.assertThat(contentCategoriaDTOList).contains(new CategoriaDTO("AUTOMOBILE", "BERLINA_COMPATTA"));
		
		
	}
	
	/**
	 * Test atto a verificare se il tipo di veicolo viene convertito in maiuscolo e sottoposto a trim
	 * @throws Exception
	 */
	@Test
	@Order(5)
	public void testTipoVeicoloNormalization() throws Exception {
		
		CategoriaRequest categoriaAutomobile = CategoriaRequest.builder()
				.categoria("CROSS")
				.tipoVeicolo("     motoveicolo     ").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		//recupero la list delle categorie per recuperare la categoria normalizzata
		MvcResult listResult = mockMvc.perform(get("/rest/categoria/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<CategoriaDTO> contentCategoriaDTOList = objectMapper.readValue(contentString, new TypeReference<List<CategoriaDTO>>() {
		});
		
		assertFalse(contentCategoriaDTOList.isEmpty());
		
		Assertions.assertThat(contentCategoriaDTOList).contains(new CategoriaDTO("MOTOVEICOLO", "CROSS"));
		
		
	}
	
	@Test
	@Order(6)
	public void testNoCategoria() throws Exception{
		CategoriaRequest categoriaAutomobile = CategoriaRequest.builder()
				.tipoVeicolo("AUTOMOBILE").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaAutomobile))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(7)
	public void testNoTipoVeicolo() throws Exception{
		CategoriaRequest categoriaNoTipo = CategoriaRequest.builder()
				.categoria("SUV").build();
		mockMvc.perform(post("/rest/categoria/create")
				.content(objectMapper.writeValueAsString(categoriaNoTipo))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(8)
	public void testDelete() throws Exception{
		
		mockMvc.perform(delete("/rest/categoria/delete/{tipoVeicolo}/{categoria}","MOTOVEICOLO","CROSS")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		MvcResult listResult = mockMvc.perform(get("/rest/categoria/list")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		
		String contentString = listResult.getResponse().getContentAsString();
		
		List<CategoriaDTO> contentCategoriaDTOList = objectMapper.readValue(contentString, new TypeReference<List<CategoriaDTO>>() {
		});
		
		assertFalse(contentCategoriaDTOList.isEmpty());
		
		assertFalse(contentCategoriaDTOList.contains(new CategoriaDTO("MOTOVEICOLO", "CROSS")));
	}
	
	/**
	 * Cancella una categiria non esistente, per il fatto che i test sono in ordine,
	 *  cancello di nuovo la categoria cancellata nel test precedente
	 * @throws Exception
	 */
	@Test
	@Order(9)
	public void testDeleteNotExisting() throws Exception{
		
		mockMvc.perform(delete("/rest/categoria/delete/{tipoVeicolo}/{categoria}","MOTOVEICOLO","CROSS")
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		
	}
	
}

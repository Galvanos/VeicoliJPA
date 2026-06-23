package com.betacom.veh.controllers;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.veh.dto.input.CategoriaRequest;
import com.betacom.veh.dto.output.CategoriaDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.dto.validation.ValidationGroups;
import com.betacom.veh.services.interfaces.ICategoriaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/categoria")
public class CategoriaController {
	private final ICategoriaService catS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@Validated(ValidationGroups.Create.class)
			@RequestBody (required = true) CategoriaRequest req) throws Exception{
			
		catS.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("created...")
					.build());
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<CategoriaDTO>> list() throws Exception{
		return ResponseEntity.ok(catS.list());
	}

	@DeleteMapping("delete/{tipoVeicolo}/{categoria}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) String tipoVeicolo, 
			@PathVariable (required = true) String categoria
			) throws Exception{
			catS.delete(CategoriaRequest.builder()
					.tipoVeicolo(tipoVeicolo)
					.categoria(categoria)
					.build());
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}

}
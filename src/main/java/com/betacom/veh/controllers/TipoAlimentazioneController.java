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
import com.betacom.veh.dto.input.TipoAlimentazioneRequest;
import com.betacom.veh.dto.output.CategoriaDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.dto.output.TipoAlimentazioneDTO;
import com.betacom.veh.dto.validation.ValidationGroups;
import com.betacom.veh.services.interfaces.ICategoriaService;
import com.betacom.veh.services.interfaces.ITipoAlimentazioneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/alimentazione")
public class TipoAlimentazioneController {

private final ITipoAlimentazioneService tipoAlimService;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@Validated(ValidationGroups.Create.class)
			@RequestBody (required = true) TipoAlimentazioneRequest req) throws Exception{
			
		tipoAlimService.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("created...")
					.build());
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<TipoAlimentazioneDTO>> list() throws Exception{
		return ResponseEntity.ok(tipoAlimService.list());
	}

	@DeleteMapping("delete/{tipoVeicolo}/{tipoAlimentazione}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) String tipoVeicolo, 
			@PathVariable (required = true) String tipoAlimentazione
			) throws Exception{
			tipoAlimService.delete(TipoAlimentazioneRequest.builder()
					.tipoVeicolo(tipoVeicolo)
					.tipoAlimentazione(tipoAlimentazione)
					.build());
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}
}

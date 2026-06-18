package com.betacom.veh.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.veh.dto.input.BiciRequest;
import com.betacom.veh.dto.input.TipoAlimentazioneRequest;
import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.dto.output.TipoAlimentazioneDTO;
import com.betacom.veh.dto.validation.ValidationGroups;
import com.betacom.veh.services.interfaces.IBiciService;
import com.betacom.veh.services.interfaces.ITipoAlimentazioneService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/tipo_alimentazione")
public class TipoAlimentazioneController {
	private final ITipoAlimentazioneService tipoAlimS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@Validated(ValidationGroups.Create.class)
			@RequestBody (required = true) TipoAlimentazioneRequest req) throws Exception{
			
		tipoAlimS.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("creato...")
					.build());
	}
	
	@DeleteMapping("delete/{tipoVeicolo}/{categoria}")
    public ResponseEntity<ResponseDTO> delete(
            @PathVariable (required = true) String tipoVeicolo, 
            @PathVariable (required = true) String tipoAlimentazione
            ) throws Exception{
		tipoAlimS.delete(TipoAlimentazioneRequest.builder()
                    .tipoVeicolo(tipoVeicolo)
                    .tipoAlimentazione(tipoAlimentazione)
                    .build());
            return ResponseEntity.ok(ResponseDTO.builder()
                    .msg("deleted...")
                    .build());
    }
	
	@GetMapping("/list")
	public ResponseEntity<List<TipoAlimentazioneDTO>> list() throws Exception{
		return ResponseEntity.ok(tipoAlimS.list());
	}
}
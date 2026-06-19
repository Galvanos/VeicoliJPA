package com.betacom.veh.controllers;

import java.util.List;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.veh.dto.input.ListVeicoloRequest;
import com.betacom.veh.dto.output.VeicoloDTO;
import com.betacom.veh.services.interfaces.IVeicoloService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/veicolo")
public class VeicoloController {
	
	private final IVeicoloService veicoloService;
	
	@GetMapping("/list")
	public ResponseEntity<List<VeicoloDTO>> list( @ParameterObject ListVeicoloRequest filters) throws Exception{
		return ResponseEntity.ok(veicoloService.findAll(filters)); 
	}
	
	@GetMapping("getById")
	public ResponseEntity<VeicoloDTO> getById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(veicoloService.findById(id));
	}

}

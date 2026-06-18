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
import com.betacom.veh.dto.output.BiciDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.dto.validation.ValidationGroups;
import com.betacom.veh.services.interfaces.IBiciService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/bici")
public class BiciController {
	private final IBiciService biciS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@Validated(ValidationGroups.Create.class)
			@RequestBody (required = true) BiciRequest req) throws Exception{
			
		biciS.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("creato...")
					.build());
	}
	
	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(
			@Validated(ValidationGroups.Update.class)
			@RequestBody (required = true) BiciRequest req) throws Exception {
			
		biciS.update(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("updated...")
					.build());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) Integer id
			) throws Exception{
			biciS.delete(id);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}
	
	@GetMapping("getById")
	public ResponseEntity<BiciDTO> getById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(biciS.getById(id));
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<BiciDTO>> list() throws Exception{
		return ResponseEntity.ok(biciS.list());
	}
}

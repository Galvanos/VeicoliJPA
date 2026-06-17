package com.betacom.veh.controllers;

import java.util.List;


import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.veh.dto.input.MotoRequest;
import com.betacom.veh.dto.validation.ValidationGroups;
import com.betacom.veh.dto.output.MotoDTO;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.services.interfaces.IMotoService;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/moto")
public class MotoController {
	private final IMotoService motoS;
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(
			@Validated(ValidationGroups.Create.class)
			@RequestBody (required = true) MotoRequest req) throws Exception{
			
		motoS.create(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("created...")
					.build());
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<MotoDTO>> list() throws Exception{
		return ResponseEntity.ok(motoS.list());
	}
	
	@GetMapping("getById")
	public ResponseEntity<MotoDTO> getById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(motoS.getById(id));
	}

	@PutMapping("update")
	public ResponseEntity<ResponseDTO> update(
			@Validated(ValidationGroups.Update.class)
			@RequestBody (required = true) MotoRequest req) throws Exception {
			
		motoS.update(req);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("updated...")
					.build());
	}

	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(
			@PathVariable (required = true) Integer id
			) throws Exception{
			motoS.delete(id);
			return ResponseEntity.ok(ResponseDTO.builder()
					.msg("deleted...")
					.build());
	}

}

package com.betacom.veh.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.veh.dto.input.AutomobileRequest;
import com.betacom.veh.dto.input.ValidationGroups;
import com.betacom.veh.dto.output.ResponseDTO;
import com.betacom.veh.services.interfaces.IAutomobileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/automobile")
public class AutomobileController {
	private final IAutomobileService carService;
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(){
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r = carService.list();
		} catch (Exception e) {
			r=e.getMessage();
			status=HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
	
	@GetMapping("getById")
	public ResponseEntity<Object> getById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(carService.getById(id));
	}
	
	@PostMapping("create")
	public ResponseEntity<ResponseDTO> create(@RequestBody (required = true) @Validated(ValidationGroups.Create.class) AutomobileRequest req) throws Exception{
		carService.create(req);
		return ResponseEntity.ok(ResponseDTO.builder().msg("created...").build());
	}
	
	@PatchMapping("update")
	public ResponseEntity<ResponseDTO> update(@RequestBody (required = true) @Validated(ValidationGroups.Create.class) AutomobileRequest req) throws Exception{
		carService.update(req);
		return ResponseEntity.ok(ResponseDTO.builder().msg("updated...").build());
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDTO> delete(@PathVariable (required = true) Integer id) {
		ResponseDTO r = new ResponseDTO();
		HttpStatus status = HttpStatus.OK;
		try {
			carService.delete(id);
			r.setMsg("deleted...");
		} catch (Exception e) {
			e.printStackTrace();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}
}

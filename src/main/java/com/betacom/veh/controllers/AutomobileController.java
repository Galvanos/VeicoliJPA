package com.betacom.veh.controllers;


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
import com.betacom.veh.dto.validation.ValidationGroups;
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
	public ResponseEntity<Object> list() throws Exception{
		return ResponseEntity.ok(carService.list());
	}
	
	@GetMapping("getById")
	public ResponseEntity<Object> getById(@RequestParam (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(carService.getById(id));
	}
	
	@PostMapping("create")
	public ResponseEntity<Object> create(@RequestBody (required = true) @Validated(ValidationGroups.Create.class) AutomobileRequest req) throws Exception{
		return ResponseEntity.ok(carService.create(req));
	}
	
	@PatchMapping("update")
	public ResponseEntity<Object> update(@RequestBody (required = true) @Validated(ValidationGroups.Update.class) AutomobileRequest req) throws Exception{
		return ResponseEntity.ok(carService.update(req));
	}
	
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Object> delete(@PathVariable (required = true) Integer id) throws Exception{
		return ResponseEntity.ok(carService.delete(id));
	}
}

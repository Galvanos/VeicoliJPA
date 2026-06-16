package com.betacom.veh.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.betacom.veh.dto.output.ResponseDTO;

/**
 * Gestore globale delle eccezioni
 */
@RestControllerAdvice
public class ExceptionManager {

	/**
	 * Gestore delle eccezioni generiche
	 * @param e l'eccezione intercettata
	 * @return un {@link ResponseEntity} con stato  {@link HttpStatus#BAD_REQUEST} con incapsulato un {@link ResponseDTO} 
	 * il cui campo {@link ResponseDTO#getMsg()} fornisce il messaggio di risposta
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> genericExceptionHandler(Exception e){
		return ResponseEntity.badRequest()
				.body(ResponseDTO.builder().msg(e.getMessage()).build());
	}
	
	/**
	 * Gestore specifico dell'eccezione di validazione degli input, 
	 * al momento viene mostrato il primo errore di validazione individuato
	 * @param e {@link MethodArgumentNotValidException} sollevata in caso di errori di validazione dell'input del rest controller
	 * @return un {@link ResponseEntity} con stato  {@link HttpStatus#BAD_REQUEST} con incapsulato un {@link ResponseDTO} 
	 * il cui campo {@link ResponseDTO#getMsg()} fornisce il messaggio di risposta
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseDTO> handleValidationException(MethodArgumentNotValidException e) {
		//si potrebbero anche mostrare tutti i messaggi, da valutare
		String msg = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("Errore di validazione");

	  return ResponseEntity.badRequest()
				.body(ResponseDTO.builder()
						.msg(msg)
						.build()
						);
	}
}

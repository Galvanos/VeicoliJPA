package com.betacom.veh.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO per contenere risposte testuali in caso di errore o in caso non si voglia restituire 
 * l'oggetto creato/aggiornato nel controller
 */

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDTO {

	/**
	 * messaggio da fornire come risposta
	 */
	String msg;
}

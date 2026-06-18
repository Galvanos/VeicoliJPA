package com.betacom.veh.dto.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class TipoAlimentazioneDTO {

	private String tipoVeicolo;
	private String tipoAlimentazione;
}

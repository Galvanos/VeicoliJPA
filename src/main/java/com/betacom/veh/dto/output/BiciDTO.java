package com.betacom.veh.dto.output;

import com.betacom.veh.dto.input.ValidationGroups;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BiciDTO extends VeicoloDTO{
	
	@NotNull(groups = ValidationGroups.Create.class, message = "Numero marce non fornito")
	private Integer numeroRapporti;
	private String tipoFreno;
	private String tipoSospensione;
	private Boolean pieghevole;
}

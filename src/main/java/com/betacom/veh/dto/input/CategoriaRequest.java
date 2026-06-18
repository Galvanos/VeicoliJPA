package com.betacom.veh.dto.input;

import com.betacom.veh.dto.validation.ValidationGroups;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaRequest {

	
	@NotNull(groups = ValidationGroups.Create.class, message = "tipo veicolo non fornito")
	private String tipoVeicolo;
	@NotNull(groups = ValidationGroups.Create.class, message = "categoria non fornita")
	private String categoria;
}

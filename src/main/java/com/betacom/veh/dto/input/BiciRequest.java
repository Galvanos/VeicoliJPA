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
public class BiciRequest extends VeicoloRequest {

    	@NotNull(groups = ValidationGroups.Create.class, message = "numero di rapporti non specificato")
	 	private Integer numeroRapporti;
        @NotNull(groups = ValidationGroups.Create.class, message = "tipo di freni non specificato")
	    private String tipoFreno;
    	@NotNull(groups = ValidationGroups.Create.class, message = "tipo di sospensioni non dichiarato")
	    private String tipoSospensione;
    	@NotNull(groups = ValidationGroups.Create.class, message = "è speghevole si o no?")
    	private Boolean pieghevole;
}
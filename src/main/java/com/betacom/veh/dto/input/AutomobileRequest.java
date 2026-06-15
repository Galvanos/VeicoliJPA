package com.betacom.veh.dto.input;

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
public class AutomobileRequest extends VeicoloRequest {

    	@NotNull(groups = ValidationGroups.Create.class, message = "targa non fornita")
	 	private String targa;
        @NotNull(groups = ValidationGroups.Create.class, message = "numero porte non fornito")
	    private Integer numeroPorte;
    	@NotNull(groups = ValidationGroups.Create.class, message = "cilindrata non fornita")
	    private Integer cc;
}

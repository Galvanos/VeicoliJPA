package com.betacom.veh.dto.input;

import com.betacom.veh.dto.validation.ValidationGroups;

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
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MotoRequest extends VeicoloRequest{
	@NotNull(groups = ValidationGroups.Create.class, message = "targa non fornita")
 	private String targa;
	@NotNull(groups = ValidationGroups.Create.class, message = "cilindrata non fornita")
    private Integer cc;

}

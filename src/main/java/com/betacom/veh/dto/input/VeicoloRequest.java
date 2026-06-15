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
public abstract class VeicoloRequest {

	@NotNull(groups = ValidationGroups.Update.class, message = "id veicolo non fornito")
	private Integer id;
    private String tipoVeicolo;
    @NotNull(groups = ValidationGroups.Create.class, message = "numero ruote non fornito")
    private Integer numeroRuote;
    @NotNull(groups = ValidationGroups.Create.class, message = "tipo alimentazione non fornito")
    private String tipoAlimentazione;
    @NotNull(groups = ValidationGroups.Create.class, message = "categoria non fornita")
    private String categoria;
    @NotNull(groups = ValidationGroups.Create.class, message = "colore non fornito")
    private String colore;
    @NotNull(groups = ValidationGroups.Create.class, message = "marca non fornita")
    private String marca;
    @NotNull(groups = ValidationGroups.Create.class, message = "anno produzione non fornito")
    private Integer annoProduzione;
    @NotNull(groups = ValidationGroups.Create.class, message = "modello non fornito")
    private String modello;
}

package com.betacom.veh.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "tipi_alimentazione_motorizzati")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class TipiAlimentazioneMotorizzati {

    @Id
    private String tipo;
}

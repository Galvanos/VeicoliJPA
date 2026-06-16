package com.betacom.veh.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categorie_automobili")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class CategorieAutomobili {

	@Id
	private String categoria;
	
	@Column(name="segmento")
	private Character segmento;
}

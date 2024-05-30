package com.example.marketStore.model;

import java.time.LocalDate;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("pedidos_fabricantes")
public class FabricanteDTO {
	
	private Integer fabricante;
	private String nombreFabricante;
	
	public Integer getFabricante() {
		return fabricante;
	}
	public void setFabricante(Integer fabricante) {
		this.fabricante = fabricante;
	}
	public String getNombreFabricante() {
		return nombreFabricante;
	}
	public void setNombreFabricante(String nombreFabricante) {
		this.nombreFabricante = nombreFabricante;
	}		

}

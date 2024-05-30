package com.example.marketStore.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("pedidos_productos")
public class ProductoDTO {
	
	private Integer producto;
	private String nombreProducto;
	public Integer getProducto() {
		return producto;
	}
	public void setProducto(Integer producto) {
		this.producto = producto;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	

}

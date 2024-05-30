package com.example.marketStore.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;


public class Producto {
	
	@Id
	private Integer id;
	private String nombreProducto;
	private String descripcion;
	private BigDecimal precio;
	private Integer stock;
	private Categoria categoria;
	
	// one to one
	private AggregateReference<Fabricante,Integer> fabricanteid;
	
	// one to many
	// le llega el muchos
	private Set<Comentario> comentarios = new HashSet<>();
	
	@Transient // Se guarda como referencia, es el id del Anuncio.
	Producto producto;
	public Set<Comentario> getComentarios() {
		return comentarios;
	}
	
	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public void addComentario(Comentario comentario) {
		comentarios.add(comentario);
		comentario.producto = this;
	}
	
	public AggregateReference<Fabricante, Integer> getFabricante() {
		return fabricanteid;
	}
	
	public void setFabricante(AggregateReference<Fabricante, Integer> fabricanteid) {
		this.fabricanteid = fabricanteid;
	}
	
	public Producto() {}
	
	public Producto(String nombreProducto, String descripcion, BigDecimal precio, Integer stock, Categoria categoria,
			AggregateReference<Fabricante, Integer> fabricanteid) {
		super();
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.categoria = categoria;
		this.fabricanteid = fabricanteid;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public BigDecimal getPrecio() {
		return precio;
	}
	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Categoria getCategoria() {
		return categoria;
	}
	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	
	

}

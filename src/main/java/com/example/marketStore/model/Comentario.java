package com.example.marketStore.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Transient;

public class Comentario {
	
	private String usuario;
    private String comentario;
    private LocalDate fechaComentario;
    private Integer valoracion;
    
    // uno a muchos
 	// le llega el one
    @Transient // Se guarda como referencia, es el id del Producto.
	Producto producto;
    
    public Comentario() {
		super();
	}

	public Comentario(String usuario, String comentario, LocalDate fechaComentario, Integer valoracion) {
		super();
		this.usuario = usuario;
		this.comentario = comentario;
		this.fechaComentario = fechaComentario;
		this.valoracion = valoracion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFechaComentario() {
		return fechaComentario;
	}

	public void setFechaComentario(LocalDate fechaComentario) {
		this.fechaComentario = fechaComentario;
	}

	public Integer getValoracion() {
		return valoracion;
	}

	public void setValoracion(Integer valoracion) {
		this.valoracion = valoracion;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
    
    

}

package com.example.marketStore.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table("pedidos")
public class Pedido {
	@Id
	private Integer id;
	private LocalDate fecha_pedido;
	private LocalDate fecha_entrega;
	private Integer cantidad;
	private BigDecimal precio_total;
	private Estado estado;
	
	private Set<FabricanteDTO> fabricantes = new HashSet<>();
	private Set<ProductoDTO> productos = new HashSet<>();
	
	public void addProducto(Producto producto) {
		ProductoDTO productoDTO = new ProductoDTO();
		productoDTO.setProducto(producto.getId());
		productoDTO.setNombreProducto(producto.getNombreProducto());
		this.productos.add(productoDTO);
	}
	
	public Set<Integer> getProductosIds(){
		return this.productos.stream()
				.map(ProductoDTO::getProducto)
				.collect(Collectors.toSet());
	}
	
	public Set<String> getProductoNombres(){
		return this.productos.stream()
				.map(ProductoDTO::getNombreProducto)
				.collect(Collectors.toSet());
	}
	
	
	public void addFabricante(Fabricante fabricante) {
		FabricanteDTO fabricanteDto = new FabricanteDTO();
		fabricanteDto.setFabricante(fabricante.getId());
		fabricanteDto.setNombreFabricante(fabricante.getNombre());
		this.fabricantes.add(fabricanteDto);
	}
	
	public Set<Integer> getFabricantesIds(){
		return this.fabricantes.stream()
				.map(FabricanteDTO::getFabricante)
				.collect(Collectors.toSet());
	}
	
	public Set<String> getFabricanteNombres(){
		return this.fabricantes.stream()
				.map(FabricanteDTO::getNombreFabricante)
				.collect(Collectors.toSet());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDate getFecha_pedido() {
		return fecha_pedido;
	}

	public void setFecha_pedido(LocalDate fecha_pedido) {
		this.fecha_pedido = fecha_pedido;
	}

	public LocalDate getFecha_entrega() {
		return fecha_entrega;
	}

	public void setFecha_entrega(LocalDate fecha_entrega) {
		this.fecha_entrega = fecha_entrega;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio_total() {
		return precio_total;
	}

	public void setPrecio_total(BigDecimal precio_total) {
		this.precio_total = precio_total;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Set<FabricanteDTO> getFabricantes() {
		return fabricantes;
	}

	public void setFabricantes(Set<FabricanteDTO> fabricantes) {
		this.fabricantes = fabricantes;
	}
	
	
}

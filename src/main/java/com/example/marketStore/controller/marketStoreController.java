package com.example.marketStore.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.marketStore.model.Categoria;
import com.example.marketStore.model.Comentario;
import com.example.marketStore.model.Estado;
import com.example.marketStore.model.Fabricante;
import com.example.marketStore.model.Pedido;
import com.example.marketStore.model.Producto;
import com.example.marketStore.repository.FabricanteRepositorio;
import com.example.marketStore.repository.PedidoRepositorio;
import com.example.marketStore.repository.ProductoRepositorio;

@Controller
@RequestMapping("/productos")
public class marketStoreController {
	
	private final FabricanteRepositorio fabricantes;
	
	private final PedidoRepositorio pedidos;
	
	private final ProductoRepositorio productos;
	
	public marketStoreController(FabricanteRepositorio fabricantes,
			PedidoRepositorio pedidos,
			ProductoRepositorio productos) {
		this.fabricantes = fabricantes;
		this.pedidos = pedidos;
		this.productos = productos;
	}
	
	@GetMapping
	public String getHomePage(Model model) {
		model.addAttribute("newFabricante", new Fabricante());
		return "home";
	}
	
	@GetMapping("/listar")
	public String getProductos(Model model) {
		model.addAttribute("productos", productos.findAll());
		return "productos";
	}
	
	@PostMapping("/ingresar")
	public String ingresarProducto(
			@ModelAttribute Fabricante fabricante,
			@RequestParam(required=true, name ="nombreProducto") String nombreProducto,
			@RequestParam(required=true, name ="descripcion") String descripcion,
			@RequestParam(required=true, name ="precio") BigDecimal precio,
			@RequestParam(required=true, name ="stock") Integer stock,
			@RequestParam(required=true, name ="categoria") String categoria,
			Model model) {
		
		AggregateReference<Fabricante,Integer> fabricanteAggregate = AggregateReference.to(
				fabricantes.save(fabricante).getId()
				);
		
		Producto producto = new Producto(nombreProducto,descripcion,precio,stock,Categoria.valueOf(categoria), fabricanteAggregate);
		
		productos.save(producto);

		model.addAttribute("productos", productos.findAll());
		
		return "productos";
	} 
	
	@GetMapping("/verFabricante/{id}")
	public String buscarFabricante(Model model,
			@PathVariable String id) {
		Optional<Fabricante> fabricanteById = fabricantes.findById(Integer.valueOf(id));
		Fabricante fabricante = fabricanteById.orElse(new Fabricante());
		model.addAttribute("fabricante", fabricante);
		return "fabricante";
	}
	
	@GetMapping("/menucomentarios")
	public String menuComentarios(Model model) {
		model.addAttribute("productos", productos.findAll());
		return "menucomentarios";
	}
	
	@GetMapping("/agregarComentario/{id}")
	public String agregarComentario(
			Model model,
			@PathVariable String id) {
		
		model.addAttribute("idProducto", id);
		return "agregarcomentario";
	}
	
	@PostMapping("/agregarComentario")
	public String nuevoComentario(
			@RequestParam(required=true, name ="idProducto") String idProducto,
			@RequestParam(required=true, name ="usuario") String usuario,
			@RequestParam(required=true, name ="comentario") String comentario,
			@RequestParam(required=true, name ="fecha_comentario") String fecha_comentario,
			@RequestParam(required=true, name ="valoracion") Integer valoracion)
			{
		
		String[] partes = fecha_comentario.split("-");
		
		LocalDate fecha = LocalDate.of(Integer.valueOf(partes[0]),
				Integer.valueOf(partes[1]),
				Integer.valueOf(partes[2])); 
		
		Comentario comentario2 = new Comentario(usuario, comentario, fecha, valoracion);
		
		Optional<Producto> productoById = productos.findById(Integer.valueOf(idProducto));
		Producto producto = productoById.orElse(new Producto());
		
		producto.addComentario(comentario2);
		productos.save(producto);
		
		return "redirect:/productos/menucomentarios";
	}
	
	@GetMapping("/verComentarios/{id}")
	public String verComentarios(
			Model model,
			@PathVariable String id) {
		
		Optional<Producto> productoById = productos.findById(Integer.valueOf(id));
		Producto producto = productoById.orElse(new Producto());
		
		Set<Comentario> comentarios = producto.getComentarios();
		
		model.addAttribute("comentarios", comentarios);
		return "comentarios";
	}
	
	@GetMapping("/pedidos")
	public String ingresarPedidos() {
		return "pedido";
	}
	
	@PostMapping("/ingresarpedido")
	public String ingresarEvento(Model model,
			@RequestParam(required=true, name ="fecha_pedido") String fecha_pedido,
			@RequestParam(required=true, name ="fecha_entrega") String fecha_entrega,
			@RequestParam(required=true, name ="cantidad") Integer cantidad,
			@RequestParam(required=true, name ="precio_total") BigDecimal precio_total,
			@RequestParam(required=true, name ="estado") String estado
			) {
		
		String[] partes = fecha_pedido.split("-");
		String[] partes2 = fecha_entrega.split("-");
		
		LocalDate fecha = LocalDate.of(Integer.valueOf(partes[0]),
				Integer.valueOf(partes[1]),
				Integer.valueOf(partes[2])); 
		
		LocalDate fecha2 = LocalDate.of(Integer.valueOf(partes2[0]),
				Integer.valueOf(partes2[1]),
				Integer.valueOf(partes2[2]));
		
		Pedido pedido = new Pedido();
		pedido.setFecha_pedido(fecha);
		pedido.setFecha_entrega(fecha2);
		pedido.setCantidad(cantidad);
		pedido.setPrecio_total(precio_total);
		pedido.setEstado(Estado.valueOf(estado));
		
		pedidos.save(pedido);
		
		model.addAttribute("pedidos", pedidos.findAll());
		
		return "pedidos";
		
	}
	
	@GetMapping("/verpedidos")
	public String verPedidos(Model model) {
		
		model.addAttribute("pedidos", pedidos.findAll());
		
		return "pedidos";
	}
	
	//AgregarProductos
	@GetMapping("/agregarProducto/{id}")
	public String agregarProducto(
			Model model,
			@PathVariable String id) {
		
		model.addAttribute("idPedido", id);
		model.addAttribute("productos",productos.findAll());
		return "agregarproducto";
	}
	
	//Agregar productos a un pedido
	@PostMapping("/asignarproducto")
	public String asignarProducto(
			Model model,
			@RequestParam(required=true, name ="idPedido") String idPedido,
			@RequestParam(required=true, name ="idProducto") String idProducto) {
		
		Optional<Producto> productoById = productos.findById(Integer.valueOf(idProducto));
		Producto producto = productoById.orElse(new Producto());
		
		Optional<Pedido> pedidoById = pedidos.findById(Integer.valueOf(idPedido));
		Pedido pedido = pedidoById.orElse(new Pedido());
		
		pedido.addProducto(producto);
		
		pedidos.save(pedido);
		
		return "redirect:/productos/verpedidos";
	}
	
	// ver todos los productos que participan en el pedido con id:
		@GetMapping("/verProductos/{id}")
		public String verProductos(
				Model model,
				@PathVariable String id) {
			
			Optional<Pedido> pedidoById = pedidos.findById(Integer.valueOf(id));
			Pedido pedido = pedidoById.orElse(new Pedido());
			
			Set<Integer> productosIds = pedido.getProductosIds();
			
			Iterable<Producto> productosDePedido = productos.findAllById(productosIds);
			
			model.addAttribute("pedido", pedido);
			model.addAttribute("productos",productosDePedido);
			return "productospedido";
		}
	
	@GetMapping("/agregarFabricante/{id}")
	public String agregarFabricante(
			Model model,
			@PathVariable String id) {
		
		model.addAttribute("idPedido", id);
		model.addAttribute("fabricantes",fabricantes.findAll());
		return "agregarfabricante";
	}
	
	// agregar un fabricante a un pedido:
	@PostMapping("/asignarfabricante")
	public String asignarFabricante(
			Model model,
			@RequestParam(required=true, name ="idPedido") String idPedido,
			@RequestParam(required=true, name ="idFabricante") String idFabricante) {
		
		Optional<Fabricante> fabricanteById = fabricantes.findById(Integer.valueOf(idFabricante));
		Fabricante fabricante = fabricanteById.orElse(new Fabricante());
		
		Optional<Pedido> pedidoById = pedidos.findById(Integer.valueOf(idPedido));
		Pedido pedido = pedidoById.orElse(new Pedido());
		
		pedido.addFabricante(fabricante);
		
		pedidos.save(pedido);
		
		return "redirect:/productos/verpedidos";
	}
			
	// ver todos los fabricantes que participan en el pedido con id:
	@GetMapping("/verfabricantes/{id}")
	public String verFabricantes(
			Model model,
			@PathVariable String id) {
		
		Optional<Pedido> pedidoById = pedidos.findById(Integer.valueOf(id));
		Pedido pedido = pedidoById.orElse(new Pedido());
		
		Set<Integer> fabricantesIds = pedido.getFabricantesIds();
		
		Iterable<Fabricante> fabricantesDePedido = fabricantes.findAllById(fabricantesIds);
		
		model.addAttribute("pedido", pedido);
		model.addAttribute("fabricantes",fabricantesDePedido);
		return "fabricantespedido";
	}
	
	//Ver los pedidos del fabricante con id:
	@GetMapping("/pedidosfabricante/{id}")
	public String verPedidosFabricante(
			Model model,
			@PathVariable String id) {
		
		Optional<Fabricante> fabricanteById = fabricantes.findById(Integer.valueOf(id));
		Fabricante fabricantes = fabricanteById.orElse(new Fabricante());
		
		Iterable<Pedido> pedidosByFabricanteId = pedidos.findByFabricanteId(fabricantes.getId());
		
		model.addAttribute("pedidos", pedidosByFabricanteId);
		
		return "pedidosdelfabricante";
	}
	
	
}

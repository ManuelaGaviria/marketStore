package com.example.marketStore.repository;

import java.util.Set;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.marketStore.model.Pedido;

public interface PedidoRepositorio extends CrudRepository<Pedido, Integer>{
	@Query("select p.* from pedidos p join pedidos_fabricantes fp on p.id = fp.pedidos where fp.fabricante = :id")
	Set<Pedido> findByFabricanteId(@Param("id") Integer id);

}

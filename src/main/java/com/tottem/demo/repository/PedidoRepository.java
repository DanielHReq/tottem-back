package com.tottem.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tottem.demo.model.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

}
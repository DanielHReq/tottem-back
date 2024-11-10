package com.tottem.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tottem.demo.model.Pedido;

@Repository
public interface PedidoRepository extends CrudRepository<Pedido, Long> {

    @Query("select p from Pedido p where p.status = 'Enviado'")
    List<Pedido> pedidosEmAberto();

    @Modifying(clearAutomatically = true)
    @Transactional(readOnly = false)
    @Query("update Pedido p set p.status ='Concluído' where p.id = ?1")
    int concluirPedido(Long id);
}
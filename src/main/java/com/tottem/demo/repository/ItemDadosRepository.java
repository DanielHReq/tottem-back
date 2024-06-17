package com.tottem.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tottem.demo.model.ItemDados;

@Repository
public interface ItemDadosRepository extends CrudRepository<ItemDados, Long> {

}
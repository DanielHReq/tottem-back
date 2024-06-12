package com.tottem.demo.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tottem.demo.model.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

    @Query("select u from Usuario u where u.celular = ?1")
    Usuario findByLogin(String login);

}
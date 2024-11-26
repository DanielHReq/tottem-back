package com.tottem.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Usuario {

    // Construtores
    public Admin () {}
    public Admin (String celular, String senha, String nome, UserRole role) {
        super.setCelular(celular);
        super.setSenha(senha);
        super.setNome(nome);
        super.setRole(role);
    }

}
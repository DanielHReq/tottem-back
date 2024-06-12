package com.tottem.demo.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends Usuario {

    // Construtores
    public Admin () {}
    public Admin (String nome, String senha, UserRole role) {
        super.setNome(nome);
        super.setSenha(senha);
        super.setRole(role);
    }



    // Métodos

    //private void alterarStatusPedido() {    }

    //private void adicionaComentario () {    }


}
package com.tottem.demo.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Usuario {

    // Construtores

    public Cliente (){}
    public Cliente (Usuario usuario) {
        this.celular = usuario.celular;
        this.senha = usuario.senha;
        this.nome = usuario.nome;
        this.role = UserRole.USER;
    }
    public Cliente (String celular, String senha, String nome, UserRole role) {
        this.celular = celular;
        this.senha = senha;
        this.nome = nome;
        this.role = role;
    }
    public Cliente (String nome, String celular, UserRole role) {
        this.nome = nome;
        this.celular = celular;
        this.role = role;
    }

    // Relações

    // 1 cliente ---realiza---> N pedidos
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<Pedido>();


    // Métodos

    public void adicionaItemCarrinho () {

    }

    public void removeItemCarrinho () {

    }

    public void concluirPedido() {

    }
}
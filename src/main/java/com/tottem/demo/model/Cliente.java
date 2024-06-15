package com.tottem.demo.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("Cliente")
public class Cliente extends Usuario {

    // Construtores
    public Cliente (String nome, String celular, Integer mesa, UserRole role) {
        this.nome = nome;
        this.celular = celular;
        this.mesa = mesa;
        this.role = role;
    }

    // Relações

    // 1 cliente ---realiza---> N pedido
    @OneToMany(mappedBy = "cliente", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Pedido> pedidos = new ArrayList<Pedido>();



    // Métodos

    public void adicionaItemCarrinho () {

    }

    public void removeItemCarrinho () {

    }

    public void concluirPedido() {

    }
}
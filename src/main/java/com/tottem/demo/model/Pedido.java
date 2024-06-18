package com.tottem.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido implements Serializable {

    // Construtores

    public Pedido() {
    }

    public Pedido(BigDecimal valor, String status, List<ItemDados> itensPedido) {
        this.valor = valor;
        this.status = status;
        this.itensPedido = itensPedido;
    }

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal valor;
    private String status;
    protected Integer mesa;

    // Relações

    // 1 pedido ---possui---> N itemDados
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItemDados> itensPedido;

    // Métodos

    void atualizaValor() {
    }

    void atualizaStatus() {
    }

    void adicionaComentario(String comentario) {
    }

    // Get / Set

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ItemDados> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemDados> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public Integer getMesa() {
        return mesa;
    }

    public void setMesa(Integer mesa) {
        this.mesa = mesa;
    }
}
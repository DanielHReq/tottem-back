package com.tottem.demo.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Pedido implements Serializable {

    // Construtores

    public Pedido () {}
    public Pedido (BigDecimal valor, String status, List<ItemDados> itensPedido){
        this.valor = valor;
        this.status =status;
        this.itensPedido = itensPedido;
    }
    public Pedido (BigDecimal valor, String status, String comentario, List<ItemDados> itensPedido){
        this.valor = valor;
        this.status =status;
        this.comentario = comentario;
        this.itensPedido = itensPedido;
    }

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal valor;
    private String status;
    private String comentario;
    
    

    // Relações

    // 1 Pedido -> 1 Cliente
    @SuppressWarnings("removal")
    @org.hibernate.annotations.ForeignKey(name="cliente_id")
    @OneToOne     // Define o tipo da relação. 1 pedido para 1 cliente
    @JsonIgnore
    private Cliente cliente;

    // 1 pedido ---possui---> N itemDados
    @OneToMany(mappedBy = "pedido", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItemDados> itensPedido;

    /** n Pedido -> n Item
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "item_pedido",
        uniqueConstraints = @UniqueConstraint (
            columnNames = {"item_id", "pedido_id"},
            name = "unique_item_pedido"
        ),
        joinColumns = @JoinColumn (
            name = "item_id",
            referencedColumnName = "id",
            table = "item",
            unique = false
        ),
        inverseJoinColumns = @JoinColumn (
            name = "pedido_id",
            referencedColumnName = "id",
            table = "pedido",
            unique = false
        )
    )
    private List<Item> itensPedido;
    */ 

    // 1 Pedido -recebe um comentario-> 1 Admin
    @SuppressWarnings("removal")
    @org.hibernate.annotations.ForeignKey(name="admin_id")
    @OneToOne      // Define o tipo da relação. 1 pedido para 1 admin
    @JsonIgnore
    private Admin admin;
    

    // Métodos

    void atualizaValor () {}

    void atualizaStatus () {}

    void adicionaComentario (String comentario) {}


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
    public String getComentario() {
        return comentario;
    }
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    public List<ItemDados> getItensPedido() {
        return itensPedido;
    }
    public void setItensPedido(List<ItemDados> itensPedido) {
        this.itensPedido = itensPedido;
    }
}
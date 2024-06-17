package com.tottem.demo.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemDados implements Serializable {

    // Construtores

    public ItemDados () {
        this.quantidade = 1;
    }
    public ItemDados (int quantidade) {
        this.quantidade = quantidade;
    }
    public ItemDados (int quantidade, Item item) {
        this.quantidade = quantidade;
        this.item = item;
    }
    

    // Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public int quantidade;
    

    // Relações

    // n ItemDados ---incrementa---> 1 Item
    @ManyToOne(optional = false)
    Item item;


    // Get / Set

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    public Item getItem() {
        return item;
    }
    public void setItem(Item item) {
        this.item = item;
    }
}
package com.tottem.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tottem.demo.model.Item;
import com.tottem.demo.repository.ItemRepository;

@RestController
@RequestMapping("/cardapio")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;


    // listar todos os itens do sistema
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Item>> mostraItens () {
        List<Item> itemList = (List<Item>) itemRepository.findAll();
        return new ResponseEntity<>(itemList, HttpStatus.OK);
    }

    // procura 1 item pelo seu id e o retorna, se existir
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<Item>> buscaItemPeloID (@PathVariable(value = "id") Long id) {
        try {
            Optional<Item> item = itemRepository.findById(id);
            List<Item> itemList = new ArrayList<Item>();

            if (item.isPresent()) itemList.add(item.get());
            else throw new Exception("Item não encontrado");

            return new ResponseEntity<>(itemList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // procura 1 item pelo seu nome e o retorna, se existir
    /*@GetMapping(value = "/{nome}", produces = "application/json")
    public ResponseEntity<Item> buscaItemPeloNome (@PathVariable(value = "nome") String nome) {
        try {
            Optional<Item> item = Optional.ofNullable(itemRepository.findByName(nome));
            return new ResponseEntity<>(item.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Item não encontrado", HttpStatus.NOT_FOUND);
        }
    }*/

    // adiciona item ao sistema
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Item> adicionaItem (@RequestBody Item item) {
        Item itemSalvo = itemRepository.save(item);
        return new ResponseEntity<>(itemSalvo, HttpStatus.OK);
    }

    // edita item existente no sistema
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Item> atualizaItem (@RequestBody Item item) {
        Item itemSalvo = itemRepository.save(item);
        return new ResponseEntity<>(itemSalvo, HttpStatus.OK);
    }

    // remove item do sistema
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void removeItem (@PathVariable("id") Long id) {
        itemRepository.deleteById(id);
    }
}

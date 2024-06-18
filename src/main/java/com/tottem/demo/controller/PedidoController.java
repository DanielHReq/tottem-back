package com.tottem.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import com.tottem.demo.model.ItemDados;
import com.tottem.demo.model.Pedido;
import com.tottem.demo.model.PedidoDTO;
import com.tottem.demo.repository.ItemDadosRepository;
import com.tottem.demo.repository.ItemRepository;
import com.tottem.demo.repository.PedidoRepository;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemDadosRepository itemDadosRepository;
    
    @Autowired
    private ItemRepository itemRepository;


    // listar todos os pedidos do sistema
    @GetMapping(value = "/", produces = "application/json")
    public ResponseEntity<List<Pedido>> mostraPedidos () {
        List<Pedido> pedidoList = (List<Pedido>) pedidoRepository.findAll();
        return new ResponseEntity<>(pedidoList, HttpStatus.OK);
    }

    // procura 1 pedido pelo seu id e o retorna, se existir
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<List<Pedido>> buscaPedidoPeloID (@PathVariable(value = "id") Long id) {
        try {
            Optional<Pedido> pedido = pedidoRepository.findById(id);
            List<Pedido> pedidoList = new ArrayList<Pedido>();

            if (pedido.isPresent()) pedidoList.add(pedido.get());
            else throw new Exception("Pedido não encontrado");

            return new ResponseEntity<>(pedidoList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity("Pedido não encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // adiciona pedido ao sistema
    /*@PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Pedido> adicionaPedido (@RequestBody Pedido pedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new ResponseEntity<>(pedidoSalvo, HttpStatus.OK);
    }*/

    // adiciona pedido ao sistema
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity<Pedido> adicionaPedido (@RequestBody PedidoDTO pedidoDTO) {

        List<ItemDados> itemDadosList = new ArrayList<ItemDados>();

        for (Map.Entry<Long, Integer> pair : pedidoDTO.itensPedido().entrySet()) {

            System.out.println(pair.getKey());
            Optional<Item> itemOptional = itemRepository.findById(pair.getKey());
            if (!itemOptional.isPresent()) return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            itemDadosList.add(new ItemDados(pair.getValue(), itemOptional.get()));
        }

        Pedido pedido = new Pedido(pedidoDTO.valor(), pedidoDTO.status(), pedidoDTO.comentario(), itemDadosList);

        itemDadosRepository.saveAll(itemDadosList);
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new ResponseEntity<>(pedidoSalvo, HttpStatus.OK);
    }

    // edita pedido existente no sistema
    @PutMapping(value = "/", produces = "application/json")
    public ResponseEntity<Pedido> atualizaPedido (@RequestBody Pedido pedido) {
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new ResponseEntity<>(pedidoSalvo, HttpStatus.OK);
    }

    // remove pedido do sistema
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public void removePedido (@PathVariable("id") Long id) {
        pedidoRepository.deleteById(id);
    }
}
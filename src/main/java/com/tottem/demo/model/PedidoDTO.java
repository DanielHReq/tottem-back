package com.tottem.demo.model;

import java.math.BigDecimal;
import java.util.Map;

public record PedidoDTO(BigDecimal valor, String status, String mesa, String celular, Map<String, String> itensPedido) {
    
}

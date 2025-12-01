package br.com.bentoquirino.api_produtos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    private Integer quantidadeVendida;
    
    private BigDecimal precoUnitario; 

}
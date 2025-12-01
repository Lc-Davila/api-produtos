package br.com.bentoquirino.api_produtos.service;

import br.com.bentoquirino.api_produtos.exception.EstoqueInsuficienteException;
// Imports para Repositórios, Models, DTOs e Transactions...

@Service
public class VendaService {
    // Injeção de dependências para Repositories (Venda, Produto, Estoque, Cliente)
    // ...

    @Transactional // Garante que a transação será revertida se houver exceção
    public Venda registrarVenda(VendaDTO vendaDto) {
        // 1. Encontrar o Cliente, Venda, e valorTotal... (conforme o plano anterior)

        for (ItemVendaDTO itemDto : vendaDto.getItens()) {
            // ... Encontra Produto e Estoque ...

            // Lógica Crítica: 2.1 Verificar a Disponibilidade
            if (estoque.getQuantidade() < itemDto.getQuantidade()) {
                // 3. Tratamento de Erros: Lança a exceção, forçando o ROLLBACK
                throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getNome());
            }

            estoque.setQuantidade(estoque.getQuantidade() - itemDto.getQuantidade());
            estoqueRepository.save(estoque); // Salva a alteração no estoque
            
        }

        return vendaRepository.save(novaVenda);
    }
}
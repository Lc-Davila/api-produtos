package com.bentao.api_produtos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bentao.api_produtos.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByNome(String nome);
    List<Produto> findByNomeContaining(String parteDoNome);
}

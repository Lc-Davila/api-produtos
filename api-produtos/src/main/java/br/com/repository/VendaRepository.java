package br.com.bentoquirino.api_produtos.repository;

import br.com.bentoquirino.api_produtos.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda, Long> {
}
package br.com.bentoquirino.api_produtos.repository;

import br.com.bentoquirino.api_produtos.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
package com.upe.eCommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.upe.eCommerce.model.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{
	
	//public List<Carrinho> findByClienteID(Long codigoCliente);
	
	@Query(value = "SELECT * FROM CARRINHO c WHERE c.CLIENTE_ID = ?1", nativeQuery = true)
	public List<Carrinho> findAllByClienteId(Long codigoCliente);

}

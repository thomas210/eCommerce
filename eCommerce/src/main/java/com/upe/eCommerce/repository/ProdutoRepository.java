package com.upe.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.eCommerce.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
	

}

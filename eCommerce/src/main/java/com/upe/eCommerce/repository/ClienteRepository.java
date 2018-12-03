package com.upe.eCommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upe.eCommerce.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}

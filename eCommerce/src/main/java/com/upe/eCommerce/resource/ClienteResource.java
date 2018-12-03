package com.upe.eCommerce.resource;


import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upe.eCommerce.model.Cliente;
import com.upe.eCommerce.repository.ClienteRepository;

@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteRepository repCliente;
	
	@GetMapping
	public List<Cliente> index() {
		
		return repCliente.findAll();
		
	}
	
	@PostMapping
	public ResponseEntity<Cliente> store (@RequestBody Cliente request) {
		
		Cliente cliente = repCliente.save(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(cliente.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(cliente);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Cliente> show (@PathVariable Long codigo) {
		
		Optional<Cliente> cliente = repCliente.findById(codigo);
		
		ResponseEntity<Cliente> res;
		if (cliente.isPresent()) {
			res = ResponseEntity.ok(cliente.get());
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@PutMapping("edit/{codigo}")
	public ResponseEntity<Cliente> update (@PathVariable Long codigo, @RequestBody Cliente request) {
		
		Optional<Cliente> cliente = repCliente.findById(codigo);
		
		ResponseEntity<Cliente> res;
		if (cliente.isPresent()) {
			request.setCodigo(cliente.get().getCodigo());
			res = ResponseEntity.ok(repCliente.save(request));
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@DeleteMapping("delete/{codigo}")
	public ResponseEntity<Cliente> destroy (@PathVariable Long codigo) {
		
		Optional<Cliente> cliente = repCliente.findById(codigo);
		
		ResponseEntity<Cliente> res;
		
		if(cliente.isPresent()) {
			repCliente.delete(cliente.get());
			res = ResponseEntity.ok(cliente.get());
			res = ResponseEntity.accepted().build();
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}

}

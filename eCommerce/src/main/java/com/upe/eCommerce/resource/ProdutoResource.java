package com.upe.eCommerce.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upe.eCommerce.model.Cliente;
import com.upe.eCommerce.model.Produto;
import com.upe.eCommerce.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping
	public List<Produto> index(){
		return produtoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Produto> store(@RequestBody Produto produto, HttpServletResponse response) {
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(produtoSalvo.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(produtoSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> show(@PathVariable Long codigo){
		Optional<Produto> produtoBuscado = produtoRepository.findById(codigo);
		
		if (!produtoBuscado.isPresent())
			return ResponseEntity.notFound().build();
			return ResponseEntity.ok(produtoBuscado.get());
	}
	
	@PutMapping("edit/{codigo}")
	public ResponseEntity<Produto> update (@PathVariable Long codigo, @RequestBody Produto request) {
		
		Optional<Produto> produto = produtoRepository.findById(codigo);
		
		ResponseEntity<Produto> res;
		if (produto.isPresent()) {
			request.setCodigo(produto.get().getCodigo());
			res = ResponseEntity.ok(produtoRepository.save(request));
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@DeleteMapping("delete/{codigo}")
	public ResponseEntity<Produto> destroy (@PathVariable Long codigo) {
		
		Optional<Produto> produto = produtoRepository.findById(codigo);
		
		ResponseEntity<Produto> res;
		
		if(produto.isPresent()) {
			produtoRepository.delete(produto.get());
			res = ResponseEntity.ok(produto.get());
			res = ResponseEntity.accepted().build();
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	

}

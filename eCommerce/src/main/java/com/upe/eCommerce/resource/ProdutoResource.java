package com.upe.eCommerce.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.upe.eCommerce.model.Produto;
import com.upe.eCommerce.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	
	@GetMapping
	public List<Produto> listar(){
		return produtoRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Produto> criar(@RequestBody Produto produto, HttpServletResponse response) {
		
		Produto produtoSalvo = produtoRepository.save(produto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{codigo}").buildAndExpand(produtoSalvo.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(produtoSalvo);
	}
	
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Produto> buscarPeloCodigo(@PathVariable Long codigo){
		Optional<Produto> produtoBuscado = produtoRepository.findById(codigo);
		
		if (!produtoBuscado.isPresent())
			return ResponseEntity.notFound().build();
			return ResponseEntity.ok(produtoBuscado.get());
	}

}

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

import com.upe.eCommerce.model.Loja;
import com.upe.eCommerce.repository.LojaRepository;

@RestController
@RequestMapping("/lojas")
public class LojaResource {
	
	@Autowired
	private LojaRepository lojaRepository;
	
	@GetMapping
	public List<Loja> index(){
		return lojaRepository.findAll();
	}
	
	@PostMapping
	public ResponseEntity<Loja> store(@RequestBody Loja request, HttpServletResponse response) {
		Loja loja = lojaRepository.save(request);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(loja.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(loja);
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Loja> show(@PathVariable Long codigo){
		Optional<Loja> loja = lojaRepository.findById(codigo);
		ResponseEntity<Loja> res;
		if (!loja.isPresent()) {
			res = ResponseEntity.notFound().build();
		}else {
			res = ResponseEntity.ok(loja.get());
		}
		return res;
	}
	
	@PutMapping("/update/{codigo}")
	public ResponseEntity<Loja> update (@RequestBody Loja request, @PathVariable Long codigo){
		Optional<Loja> loja = lojaRepository.findById(codigo);
		ResponseEntity<Loja> res;
		if (loja.isPresent()) {
			request.setCodigo(loja.get().getCodigo());
			res = ResponseEntity.ok(lojaRepository.save(request));
		}else {
			res = ResponseEntity.notFound().build();
		}		
		return res;
	}
	
	@DeleteMapping("/delete/{codigo}")
	public ResponseEntity<Loja> destroy (@PathVariable Long codigo) {
		Optional<Loja> loja = lojaRepository.findById(codigo);
		ResponseEntity<Loja> res;
		if (loja.isPresent()) {
			lojaRepository.delete(loja.get());
			res = ResponseEntity.ok(loja.get());
		}else {
			res = ResponseEntity.notFound().build();
		}		
		return res;
	}

}

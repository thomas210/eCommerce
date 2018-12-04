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

import com.upe.eCommerce.model.Carrinho;
import com.upe.eCommerce.model.ProdutoVenda;
import com.upe.eCommerce.model.Venda;
import com.upe.eCommerce.repository.CarrinhoRepository;
import com.upe.eCommerce.repository.ProdutoVendaRepository;
import com.upe.eCommerce.repository.VendaRepository;

@RestController
@RequestMapping("/vendas")
public class VendaResource {
	
	@Autowired
	private VendaRepository repVenda;
	
	@Autowired
	private CarrinhoRepository repCarrinho;
	
	@Autowired
	private ProdutoVendaRepository repProdutoVenda;
	
	@GetMapping
	public List<Venda> index() {
		return repVenda.findAll();
	}
	
	@PostMapping("/{codigoClinete}")
	public ResponseEntity<Venda> store(@RequestBody Venda request, @PathVariable Long codigoCliente) {
		Venda venda = repVenda.save(request);
		
		List<Carrinho> produtosCarrinho = repCarrinho.findAllByClienteId(codigoCliente);
		
		repCarrinho.deleteAll(produtosCarrinho);
		
		List<ProdutoVenda> produtosVenda = venda.addProdutoVenda(produtosCarrinho);
		
		for (int i = 0; i < produtosVenda.size(); i++) {
			repProdutoVenda.save(produtosVenda.get(i));
		}
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(venda.getCodigo()).toUri();
		
		return ResponseEntity.created(uri).body(venda);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Venda> show(@PathVariable Long codigo) {
		
		Optional<Venda> venda = repVenda.findById(codigo);
		
		ResponseEntity<Venda> res;
		
		if(venda.isPresent()) {
			res = ResponseEntity.ok(venda.get());
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	

	@PutMapping("edit/{codigo}")
	public ResponseEntity<Venda> update (@PathVariable Long codigo, @RequestBody Venda request) {
		
		Optional<Venda> venda = repVenda.findById(codigo);
		
		ResponseEntity<Venda> res;
		if (venda.isPresent()) {
			request.setCodigo(venda.get().getCodigo());
			res = ResponseEntity.ok(repVenda.save(request));
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@DeleteMapping("delete/{codigo}")
	public ResponseEntity<Venda> destroy (@PathVariable Long codigo) {
		
		Optional<Venda> venda = repVenda.findById(codigo);
		
		ResponseEntity<Venda> res;
		
		if(venda.isPresent()) {
			repVenda.delete(venda.get());
			res = ResponseEntity.ok(venda.get());
			res = ResponseEntity.accepted().build();
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	

}

package com.upe.eCommerce.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import com.upe.eCommerce.model.Cliente;
import com.upe.eCommerce.model.Produto;
import com.upe.eCommerce.repository.CarrinhoRepository;
import com.upe.eCommerce.repository.ClienteRepository;
import com.upe.eCommerce.repository.ProdutoRepository;



@RestController
@RequestMapping("/carrinho")
public class CarrinhoResource {
	
	@Autowired
	private CarrinhoRepository repCarrinho;
	
	@Autowired
	private ClienteRepository repCliente;
	
	@Autowired
	private ProdutoRepository repProduto;
	
	@GetMapping
	public List<Carrinho> index() {
		
		return repCarrinho.findAll();
		
	}
	
	@PostMapping("/{codigoCliente}/{codigoProduto}")
	public ResponseEntity<Carrinho> store (@RequestBody Carrinho request, @PathVariable Long codigoCliente, @PathVariable Long codigoProduto) {
		
		Cliente cliente = repCliente.findById(codigoCliente).get();
		
		Produto produto = repProduto.findById(codigoProduto).get();
		
		ResponseEntity<Carrinho> res;
		
		if (produto.isEstoqueDisponivel(request.getQuantidade())) {
			
			produto.setEstoque_un(produto.getEstoque_un() - request.getQuantidade());
			
			repProduto.save(produto);
			
			request.setCliente(cliente);
			
			Produto p = produto;
			
			request.setProduto(p);
			
			request.aplicarDesconto();
			
			Carrinho carrinho = repCarrinho.save(request);
			
			URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").buildAndExpand(carrinho.getCodigo()).toUri();
			
			res = ResponseEntity.created(uri).body(carrinho);
			
		} else {
			
			res = ResponseEntity.badRequest().build();
			
		}
		
		return res;
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Carrinho> show (@PathVariable Long codigo) {
		
		Optional<Carrinho> carrinho = repCarrinho.findById(codigo);
		
		ResponseEntity<Carrinho> res;
		if (carrinho.isPresent()) {
			res = ResponseEntity.ok(carrinho.get());
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@PutMapping("edit/{codigo}")
	public ResponseEntity<Carrinho> update (@PathVariable Long codigo, @Valid @RequestBody Carrinho request) {
		
		Optional<Carrinho> carrinho = repCarrinho.findById(codigo);
		
		ResponseEntity<Carrinho> res;
		if (carrinho.isPresent()) {
			request.setCodigo(carrinho.get().getCodigo());
			res = ResponseEntity.ok(repCarrinho.save(request));
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}
	
	@DeleteMapping("delete/{codigo}")
	public ResponseEntity<Carrinho> destroy (@PathVariable Long codigo) {
		
		Optional<Carrinho> carrinho = repCarrinho.findById(codigo);
		
		ResponseEntity<Carrinho> res;
		
		if(carrinho.isPresent()) {
			repCarrinho.delete(carrinho.get());
			res = ResponseEntity.ok(carrinho.get());
			res = ResponseEntity.accepted().build();
		}else {
			res = ResponseEntity.notFound().build();
		}
		
		return res;
	}

}

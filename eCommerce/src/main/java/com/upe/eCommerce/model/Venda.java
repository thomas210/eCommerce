package com.upe.eCommerce.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "loja_id")
	private Loja loja;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	private Date dataVenda;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Loja getLoja() {
		return loja;
	}

	public void setLoja(Loja loja) {
		this.loja = loja;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public List<ProdutoVenda> addProdutoVenda(List<Carrinho> produtosCarrinho) {
		
		List<ProdutoVenda> res = new ArrayList<ProdutoVenda>();
		
		for (int i = 0; i < produtosCarrinho.size(); i++) {
			Carrinho pCarrinho = produtosCarrinho.get(i);
			ProdutoVenda vProduto = new ProdutoVenda();
			vProduto.setProduto(pCarrinho.getProduto());
			vProduto.setQuantidade(pCarrinho.getQuantidade());
			vProduto.setVenda(this);
			res.add(vProduto);
		}
		
		return res;
	}
	
	
	

}

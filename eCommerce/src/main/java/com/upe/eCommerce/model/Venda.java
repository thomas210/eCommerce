package com.upe.eCommerce.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "vendas")
public class Venda {
	
	private static double ACRESCIMO_PARCELA = 0.1;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	private double precoTotal;
	
	private int parcelas;
	
	//@OneToMany(mappedBy = "venda")
	@OneToMany
	@JoinColumn(name = "venda_id")
	private List<ProdutoVenda> produtosVenda;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public int getParcelas() {
		return parcelas;
	}

	public void setParcelas(int parcelas) {
		this.parcelas = parcelas;
	}

	public List<ProdutoVenda> getProdutosVenda() {
		return produtosVenda;
	}

	public void setProdutosVenda(List<ProdutoVenda> produtosVenda) {
		this.produtosVenda = produtosVenda;
	}

	

	public List<ProdutoVenda> addProdutoVenda(List<Carrinho> produtosCarrinho) {
		
		List<ProdutoVenda> res = new ArrayList<ProdutoVenda>();
		
		for (int i = 0; i < produtosCarrinho.size(); i++) {
			Carrinho pCarrinho = produtosCarrinho.get(i);
			ProdutoVenda vProduto = new ProdutoVenda();
			vProduto.setProduto(pCarrinho.getProduto());
			vProduto.setQuantidade(pCarrinho.getQuantidade());
			vProduto.setPrecoProduto(pCarrinho.getPrecoProduto());
			//vProduto.setVenda(this);
			vProduto.setVenda_id(this.getCodigo());
			res.add(vProduto);
			this.precoTotal += vProduto.getPrecoProduto() * vProduto.getQuantidade();
		}
		
		return res;
	}
	
	public void finalizarCompra() {
		
		if (this.parcelas > 1) {
			double porcJuros = Venda.ACRESCIMO_PARCELA * this.parcelas;
			this.precoTotal += this.precoTotal * porcJuros;
		}
		
	}
	
	
	

}

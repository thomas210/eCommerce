package com.upe.eCommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "carrinho")
public class Carrinho {
	
	private static double DESCONTO_ATACADO = 0.05;
	private static int QUANTIDADE_ATACADO = 5;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	private int quantidade;
	
	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;
	
	private double precoProduto;

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public double getPrecoProduto() {
		return precoProduto;
	}

	public void setPrecoProduto(double precoProduto) {
		this.precoProduto = precoProduto;
	}

	public void aplicarDesconto() {
		if (this.quantidade > Carrinho.QUANTIDADE_ATACADO) {
			double desconto = this.produto.getPreco() * Carrinho.DESCONTO_ATACADO;
			this.setPrecoProduto(this.produto.getPreco() - desconto);
		
		}
	}
	

}

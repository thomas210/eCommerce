package com.upe.eCommerce.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "produto_venda")
public class ProdutoVenda {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	
	private int quantidade;
	
	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "venda_id")
	private Venda venda;*/
	private Long venda_id;

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

	public Long getVenda_id() {
		return venda_id;
	}

	public void setVenda_id(Long venda_id) {
		this.venda_id = venda_id;
	}

	/*public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}*/
	
	
	
	

}

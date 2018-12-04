package com.upe.eCommerce.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	@NotNull
	@Size(max = 50)
	private String categoria;
	@NotNull
	@Size(max = 50)
	private String descricao;
	private int estoque_un;
	@NotNull
	private double preco;
	@NotNull
	private long loja_id;
	
	
	public long getLoja_id() {
		return loja_id;
	}
	public void setLoja_id(long loja_id) {
		this.loja_id = loja_id;
	}
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public int getEstoque_un() {
		return estoque_un;
	}
	public void setEstoque_un(int estoque) {
		this.estoque_un = estoque;
	}
	public double getPreco() {
		return preco;
	}
	public void setPreco(double preco) {
		this.preco = preco;
	}
	
	
	

}

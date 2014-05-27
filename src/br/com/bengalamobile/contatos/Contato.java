package br.com.bengalamobile.contatos;

import java.io.Serializable;

public class Contato implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Contato(String nome, String tel) {
		this.nome = nome;
		this.tel = tel;
	}
	
	private String nome;
	private String tel;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		return nome;
	}
}

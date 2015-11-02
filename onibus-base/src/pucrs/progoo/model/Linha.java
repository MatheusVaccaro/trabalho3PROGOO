package pucrs.progoo.model;

import java.util.ArrayList;

public class Linha {
	private ArrayList<Coordenada> coordenadas;
	private ArrayList<Parada> paradas;
	private String idLinha;
	private String nome;
	private String codigo;
	private char tipo;
	
	public Linha() {
		
	}
	
	public Linha(ArrayList<Coordenada> coordenadas, ArrayList<Parada> paradas,
			String idLinha, String nome, String codigo, char tipo) {

		this.coordenadas = coordenadas;
		this.paradas = paradas;
		this.idLinha = idLinha;
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
	}
	
	public Linha(String idLinha, String nome, String codigo, char tipo) {

		this.idLinha = idLinha;
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
	}
		

	public ArrayList<Coordenada> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<Coordenada> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public ArrayList<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(ArrayList<Parada> paradas) {
		this.paradas = paradas;
	}
	
	public String getIdLinha(){
		return idLinha;
	}
	
	public void setIdLinha(String idLinha){
		this.idLinha = idLinha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public char getTipo() {
		return tipo;
	}

	public void setTipo(char tipo) {
		this.tipo = tipo;
	}	
	
}

package pucrs.progoo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jxmapviewer.viewer.GeoPosition;

public class Linha {
	private ArrayList<GeoPosition> coordenadas;
	private Map<String, Parada> paradas;
	private String idLinha;
	private String nome;
	private String codigo;
	private char tipo;
	
	
	public Linha(String idLinha, String nome, String codigo, char tipo) {
		
		this.paradas = new HashMap<>();
		this.coordenadas = new ArrayList<>();
		this.idLinha = idLinha;
		this.nome = nome;
		this.codigo = codigo;
		this.tipo = tipo;
	}
		

	public ArrayList<GeoPosition> getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(ArrayList<GeoPosition> coordenadas) {
		this.coordenadas = coordenadas;
	}

	public Map<String, Parada> getParadas() {
		return paradas;
	}

	public void setParadas(Map<String, Parada> paradas) {
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

	@Override
	public String toString() {
		return this.nome + " - " + this.idLinha;
	}	
	
	
}

package pucrs.progoo.model;

import org.jxmapviewer.viewer.GeoPosition;

public class Parada{
		
	private String idParada;
	private char terminal;
	private GeoPosition coordenadas;
	
	public Parada(){
		
	}
	
	public Parada(String idParada, GeoPosition coordenadas, char terminal){		
		this.idParada = idParada;		
		this.coordenadas = coordenadas;
		this.terminal = terminal;
	}	

	public String getIdParada() {
		return idParada;
	}

	public void setIdParada(String idParada) {
		this.idParada = idParada;
	}

	public GeoPosition getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(GeoPosition coordenadas) {
		this.coordenadas = coordenadas;
	}

	public char getTerminal() {
		return terminal;
	}

	public void setTerminal(char terminal) {
		this.terminal = terminal;
	}

	@Override
	public String toString() {
		return "ID da Parada: " + idParada + " | Terminal: " + terminal;
	}

	
	
}

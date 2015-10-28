package pucrs.progoo.geo;

import java.awt.Color;
import java.util.ArrayList;

import org.jxmapviewer.viewer.GeoPosition;

public class Tracado {
	
	private ArrayList<GeoPosition> pontos;
	private Color cor;
	
	public Tracado() {
		pontos = new ArrayList<>();
	}
	
	public void clear() {
		pontos.clear();
	}
	
	public int size() {
		return pontos.size();
	}
	
	public void addPonto(GeoPosition pos) {
		pontos.add(pos);
	}
	
	public ArrayList<GeoPosition> getPontos() {
		return pontos;
	}
	
	public void setCor(Color cor) {
		this.cor = cor;
	}
	
	public Color getCor() { 
		return cor;
	}
}

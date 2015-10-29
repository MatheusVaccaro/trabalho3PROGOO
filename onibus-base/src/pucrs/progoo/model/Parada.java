package pucrs.progoo.model;

public class Parada {
		
	private int idParada;
	private double longitude;
	private double latitude;
	private char terminal;
	
	public Parada(){
		
	}
	
	public Parada(int idParada,double longitude, double latitude, char terminal){		
		this.idParada = idParada;		
		this.longitude = longitude;
		this.latitude = latitude;
		this.terminal = terminal;
	}	

	public int getIdParada() {
		return idParada;
	}

	public void setIdParada(int idParada) {
		this.idParada = idParada;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public char getTerminal() {
		return terminal;
	}

	public void setTerminal(char terminal) {
		this.terminal = terminal;
	}
	
	
}

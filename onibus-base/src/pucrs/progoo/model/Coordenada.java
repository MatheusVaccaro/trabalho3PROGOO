package pucrs.progoo.model;

public class Coordenada {
	private int id;
	private int idCoordenada;
	private double latitude;
	private double longitude;	
	
	public Coordenada() {
		
	}
	
	public Coordenada(int id, int idCoordenada, double latitude, double longitude) {
		this.id = id;
		this.idCoordenada = idCoordenada;
		this.latitude = latitude;
		this.longitude = longitude;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdCoordenada() {
		return idCoordenada;
	}

	public void setIdCoordenada(int idCoordenada) {
		this.idCoordenada = idCoordenada;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
		
}

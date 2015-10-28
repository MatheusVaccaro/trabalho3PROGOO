package pucrs.progoo.model;

public class Parada {
	
	private int id;
	private int idParada;
	private int codigo;
	private double longitude;
	private double latitude;
	private char terminal;
	
	public Parada(){
		
	}
	
	public Parada(int id, int idParada, int codigo, double longitude, double latitude, char terminal){
		this.id = id;
		this.idParada = idParada;
		this.codigo = codigo;
		this.longitude = longitude;
		this.latitude = latitude;
		this.terminal = terminal;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdParada() {
		return idParada;
	}

	public void setIdParada(int idParada) {
		this.idParada = idParada;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
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

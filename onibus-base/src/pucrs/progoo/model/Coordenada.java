package pucrs.progoo.model;

public class Coordenada {
	
	private String latitude;
	private String longitude;	
	
	public Coordenada() {
		
	}
	
	public Coordenada(String latitude, String longitude) {
		this.latitude = latitude;
		this.longitude = longitude;		
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	@Override
	public String toString() {
		return "Coordenada [latitude=" + latitude + ", longitude=" + longitude
				+ "]";
	}
		
	
}

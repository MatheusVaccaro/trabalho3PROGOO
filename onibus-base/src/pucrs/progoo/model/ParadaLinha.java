package pucrs.progoo.model;

public class ParadaLinha {
	private int id;
	private int idLinha;
	private int idParada;
	
	public ParadaLinha() {
		
	}
	
	public ParadaLinha(int id,int idLinha, int idParada) {
		this.id = id;
		this.idLinha = idLinha;
		this.idParada = idParada;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdLinha() {
		return idLinha;
	}

	public void setIdLinha(int idLinha) {
		this.idLinha = idLinha;
	}

	public int getIdParada() {
		return idParada;
	}

	public void setIdParada(int idParada) {
		this.idParada = idParada;
	}	
}

package Modele;

public class Chambre {

	private int id;
	private boolean prise;
	private int idHotel;
	private int typeDeChambre;
	private int Service;
	private int numero;
	
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isPrise() {
		return prise;
	}
	public void setPrise(boolean prise) {
		this.prise = prise;
	}
	public int getIdHotel() {
		return idHotel;
	}
	public void setIdHotel(int idHotel) {
		this.idHotel = idHotel;
	}
	public int getTypeDeChambre() {
		return typeDeChambre;
	}
	public void setTypeDeChambre(int typeDeChambre) {
		this.typeDeChambre = typeDeChambre;
	}
	public int getService() {
		return Service;
	}
	public void setService(int service) {
		Service = service;
	}
	
	
	
}

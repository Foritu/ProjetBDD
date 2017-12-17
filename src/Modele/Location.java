package Modele;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Location {

	private Date dateDebut;
	private Date dateRendu;
	private int idChambre;

	public java.sql.Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateRendu() {
		return dateRendu;
	}

	public void setDateRendu(Date dateRendu) {
		this.dateRendu = dateRendu;
	}

	public int getIdChambre() {
		return idChambre;
	}

	public void setIdChambre(int idChambre) {
		this.idChambre = idChambre;
	}
	
	public int NbJour(){
		return (int) ChronoUnit.DAYS.between(LocalDate.parse(this.getDateDebut().toString()),LocalDate.parse(this.getDateRendu().toString()));
	}
	
}

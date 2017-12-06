package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;

import Modele.Location;
import Modele.SingletonConnection;

public class LocationDao extends DAO<Location> {

	Connection conn = SingletonConnection.getConnection();
	
	public LocationDao(Connection conn) {
		super(conn);
	}

	public boolean create(Location obj) {
		boolean res = false;
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("insert into Location (dateDebut,dateFin,idChambre) values (?,?,?)");
			prep.setDate(1, obj.getDateDebut());
			prep.setDate(2, obj.getDateRendu());
			prep.setInt(3, obj.getIdChambre());
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean delete(Location obj) {
		boolean res = false;
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("delete from Location where idChambre=?");
			prep.setInt(1, obj.getIdChambre());
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public boolean update(Location obj) {
		boolean res=false;
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("Update Location set dateFin=? where idChambre=?");
			prep.setDate(1, obj.getDateRendu());
			prep.setInt(2, obj.getIdChambre());
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public Location find(int idChambre) {
		Location loc = new Location();
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("Select * from Location where idChambre=?");
			prep.setInt(1, idChambre);
			ResultSet resu = prep.executeQuery();
			if(resu.next()){
				loc.setDateDebut(resu.getDate("dateDebut"));
				loc.setDateRendu(resu.getDate("dateFin"));
				loc.setIdChambre(idChambre);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loc;
	}
	
	public double prix(Location obj){
		double res=0;
		int typeDeChambre=0, service=0;
		int nbJours = obj.NbJour();
		try {
			PreparedStatement prep = conn.prepareStatement("Select typeDeChambre from Chambre where id=?");
			prep.setInt(1, obj.getIdChambre());
			ResultSet resu = prep.executeQuery();
			if(resu.next())
				typeDeChambre = resu.getInt("typeDeChambre");
			if(typeDeChambre==1)
				res = nbJours *30;
			else
				res = nbJours*30*(typeDeChambre-0.5);
			prep = conn.prepareStatement("Select Service from Chambre where id=?");
			prep.setInt(1, obj.getIdChambre());
			resu = prep.executeQuery();
			if(resu.next())
				service = resu.getInt("Service");
			if(service ==1)
				res +=20;
			if(service>=2 && service<=5)
				res +=15;
			if(service>=6 && service<=11)
				res +=10;
			if(service>=12 && service<=15)
				res+=5;
			if(service ==16)
				res+=0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}

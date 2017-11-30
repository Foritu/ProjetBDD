package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		Location loc = null;
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

}

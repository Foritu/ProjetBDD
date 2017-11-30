package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import Modele.Hotel;
import Modele.SingletonConnection;

public class HotelDao extends DAO<Hotel> {

	Connection conn = SingletonConnection.getConnection();
	
	public HotelDao(Connection conn){
		super(conn);
	}

	public boolean create(Hotel obj) {
		boolean res = false;
		try {
			PreparedStatement prep = conn.prepareStatement("insert into Hotel (nom,nbChambres,Etoiles) values (?,?,?)");
			prep.setString(1, obj.getNom());
			prep.setInt(2, obj.getNbChambres());
			prep.setInt(3, obj.getEtoiles());
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean delete(Hotel obj) {
		try {
			PreparedStatement pep = conn.prepareStatement("delete from chambre where idHotel=?");
			pep.setInt(1, obj.getId());
			pep.executeUpdate();
			PreparedStatement prep = conn.prepareStatement("delete from Hotel where idHotel=?");//A revoir, utilisateur ne connait pas id
			prep.setInt(1, obj.getId());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return false;
	}

	public boolean update(Hotel obj) {	
		return false;
	}

	public Hotel find(int id) {
		Hotel h = new Hotel();
		try {
			PreparedStatement prep = conn.prepareStatement("Select * from Hotel where idHotel=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			if(res.next()){
				h.setId(res.getInt("idHotel"));	
				h.setNbChambres(res.getInt("nbChambres"));
				h.setNom(res.getString("nom"));
				h.setEtoiles(res.getInt("Etoiles"));
			}
			else
				h=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	public Hotel findByName(String nom){
		Hotel h = new Hotel();
		try {
			PreparedStatement prep = conn.prepareStatement("Select * from Hotel where nom=?");
			prep.setString(1, nom);
			ResultSet res = prep.executeQuery();
			if(res.next()){
				h.setId(res.getInt("idHotel"));	
				h.setNbChambres(res.getInt("nbChambres"));
				h.setNom(res.getString("nom"));
				h.setEtoiles(res.getInt("Etoiles"));
			}
			else
				h=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return h;
	}
	
	public boolean IncChambre(int id){
		boolean res = false;
		try {
			PreparedStatement pep = conn.prepareStatement("Select nbChambres from Hotel where idHotel=?");
			pep.setInt(1, id);
			ResultSet resu = pep.executeQuery();
			resu.next();
			PreparedStatement prep = conn.prepareStatement("update Hotel set nbChambres=? where idHotel=?");
			prep.setInt(1, resu.getInt(1));
			prep.setInt(2, id);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return res;
	}

	public boolean print() {
		boolean res = false;
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("Select nom from Hotel");
			ResultSet resu = prep.executeQuery();
			System.out.println("Liste des noms des Hotels");
			while(resu.next()){
				System.out.println(resu.getString(1));
			}
			res = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
		
}

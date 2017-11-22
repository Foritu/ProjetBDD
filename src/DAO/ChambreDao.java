package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Modele.Chambre;
import Modele.Hotel;
import Modele.SingletonConnection;

public class ChambreDao extends DAO<Chambre> {

	Connection conn = SingletonConnection.getConnection();
	
	public ChambreDao(Connection conn) {
		super(conn);
	}

	public boolean create(Chambre obj) {
		boolean res=false;
		try {
			PreparedStatement pep = conn.prepareStatement("Select Max(numero) From Chambre where idHotel=?"); //Récupération du nombre de chambres dans l'hotel
			pep.setInt(1, obj.getIdHotel());
			ResultSet resu = pep.executeQuery();
			resu.next();
			int t = resu.getInt(1)+1;
			System.out.println(t);
			PreparedStatement prep = conn.prepareStatement("insert into Chambre (prise,idHotel,typeDeChambre,Service,numero) values(?,?,?,?,?)");
			prep.setBoolean(1, obj.isPrise());
			prep.setInt(2, obj.getIdHotel());
			prep.setInt(3, obj.getTypeDeChambre());
			prep.setInt(4, obj.getService());
			prep.setInt(5, t);
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	public boolean delete(Chambre obj) {
		boolean res = false;
		try {
			PreparedStatement prep = conn.prepareStatement("delete from chambre where id=?");//A revoir
			prep.setInt(1, obj.getId());
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return res;
	}

	public boolean update(Chambre obj) {
		return false;
	}

	public Chambre find(int id) {
		Chambre c = new Chambre();
		try {
			PreparedStatement prep= conn.prepareStatement("select * from chambre where id=?");
			prep.setInt(1, id);
			ResultSet res = prep.executeQuery();
			if(res.next()){
				c.setId(id);
				c.setIdHotel(res.getInt("idHotel"));
				c.setPrise(res.getBoolean("prise"));
				c.setService(res.getInt("service"));
				c.setTypeDeChambre(res.getInt("typeDeChambre"));
			}
			else 
				c=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int findChambreLibre(String nom, int type){
		int res = 0;
		try{
			HotelDao hDao = new HotelDao(conn);
			Hotel h = hDao.findByName(nom);
			int id = h.getId(); 
			PreparedStatement prep = conn.prepareStatement("select id from Chambre where idHotel=? and prise=FALSE and typeDeChambre=?");
			prep.setInt(1, id);
			prep.setInt(2, type);
			ResultSet resu = prep.executeQuery();
			if(resu.next())
				res=resu.getInt("id");
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean setService(int service, int idChambre){
		boolean res = false;
		try {
			PreparedStatement prep = conn.prepareStatement("update Chambre set 'service'=? where id=?");
			prep.setInt(1, service);
			prep.setInt(2, idChambre);
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean louer(int id,int service){
		boolean res=false;
		try {
			PreparedStatement prep = conn.prepareStatement("update Chambre set 'prise'=TRUE 'service'=? where id=?");
			prep.setInt(1, service);
			prep.setInt(2, id);
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public boolean deleteByNum(int num,int idHotel){
		boolean res =false;
		PreparedStatement prep;
		try {
			prep = conn.prepareStatement("delete from chambre where idHotel=? and numero=?");
			prep.setInt(1,idHotel);
			prep.setInt(2, num);
			prep.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return res;
	}

}

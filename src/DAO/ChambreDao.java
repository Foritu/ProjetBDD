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
	
	//Renvoie une variable chambre contenant les données de la chambre d'id id
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
				c.setNumero(res.getInt("numero"));
			}
			else 
				c=null;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	
	//Renvoie l'identifiant de la premiere chambre de libre correspondant au type et étant dans l'hotel voulu
	public int findChambreLibre(String nom, int type){
		int res = -1;
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
	
	
	//Modifie les service de la chambre d'id idChambre
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
	
	//Loue la chambre d'id id en lui associant les services service
	public boolean louer(int id,int service){
		boolean res=false;
		try {
			PreparedStatement prep = conn.prepareStatement("Update Chambre set prise=? ,service=? where id=?");
			prep.setBoolean(1, true);
			prep.setInt(2, service);
			prep.setInt(3, id);
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	//Supprime une chambre suivant son numero et son hotel (useless ??)
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
	
	public boolean rendreChambre(int numero,String nomHotel){
		boolean res=false;
		PreparedStatement prep;
		HotelDao hDao = null;
		Hotel h = hDao.findByName(nomHotel);
		int idHotel = h.getId();
		try {
			prep = conn.prepareStatement("Update from Chambre set prise='false', service='16' where idhotel=? and numero=?");
			prep.setInt(1, idHotel);
			prep.setInt(2, numero);
			prep.executeUpdate();
			res=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}

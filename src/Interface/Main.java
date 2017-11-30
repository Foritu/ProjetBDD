package Interface;

import java.io.Console;
import java.sql.Connection;
import java.util.Scanner;

import DAO.ChambreDao;
import DAO.HotelDao;
import Modele.Chambre;
import Modele.Hotel;
import Modele.SingletonConnection;

public class Main {

	static Connection conn = SingletonConnection.getConnection();
	static Scanner clavier = new Scanner(System.in);
    static Console console = System.console();        
	
	public static void main(String[] args) {
		
		int rep;
		HotelDao hDao = new HotelDao(conn);
		ChambreDao cDao = new ChambreDao(conn);
		
		System.out.println("Bienvenue");
		do{
		System.out.println("Que voulez vous faire ?");
		System.out.println("0 : Quitter");
		System.out.println("1 : Ajouter un Hotel.");
		System.out.println("2 : Supprimer un Hotel.");
		System.out.println("3 : Ajouter une chambre.");
		System.out.println("4 : Supprimer une chambre.");
		
		rep=clavier.nextInt();
		switch(rep){
		case 1:AjoutHotel();
				break;
		case 2:SupprHotel();
				break;
		case 3:AjoutChambre();
				break;
		case 4:SupprChambre();
				break;
		}
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		}while(rep!=0);
		System.out.println("Au revoir.");
	}
	
	public static void AjoutHotel(){
		Hotel h = new Hotel();
		HotelDao hDao = new HotelDao(conn);
		
		System.out.println("Quel est le nom de l'Hotel ?");
		String nom = clavier.next();
		h.setNom(nom);
		if(hDao.findByName(nom)==null){
			System.out.println("Combien de chambre dans cet Hotel ?");
			int nbChambre = clavier.nextInt();
			h.setNbChambres(nbChambre);
			System.out.println("Combien d'etoiles a cet Hotel ?");
			int Etoiles = clavier.nextInt();
			h.setEtoiles(Etoiles);
			hDao.create(h);
			h=hDao.findByName(nom);
			AjoutChambreCreationHotel(nbChambre,h.getId());
		}
		else{
			System.out.println("Un hotel portant ce nom existe deja.");
		}
		
	}
	
	public static void AjoutChambreCreationHotel(int nb,int idHotel){
		Chambre c = new Chambre();
		ChambreDao cDao = new ChambreDao(conn);
		c.setIdHotel(idHotel);
		c.setPrise(false);
		c.setService(16);
		
		for(int i=1;i<=nb;i++){
			int t = TypeChambre();
			c.setTypeDeChambre(t);
			cDao.create(c);
		}
	}
	
	public static int TypeChambre(){
		System.out.println("De quel type est la chambre ?");
		System.out.println("1: Simple \n2: Double \n3: Suite");
		return clavier.nextInt();
	}
	
	public static void SupprHotel(){
		HotelDao hDao = new HotelDao(conn);
		
		System.out.println("Quel est le nom de l'Hotel que vous voulez supprimer ?");
		String nom = clavier.next();
		Hotel h = hDao.findByName(nom);
		System.out.println(h.getId());
		hDao.delete(h);
	}
	
	public static void AjoutChambre(){
		Chambre c = new Chambre();
		ChambreDao cDao = new ChambreDao(conn);
		HotelDao hDao = new HotelDao(conn);

		System.out.println("Quel est le nom de l'Hotel ou est cette chambre ?");
		String nom = clavier.next();
		int t = TypeChambre();
		c.setIdHotel(hDao.findByName(nom).getId());
		c.setPrise(false);
		c.setService(16);
		c.setTypeDeChambre(t);
		cDao.create(c);
		hDao.IncChambre(c.getId());
	}
	
	public static void SupprChambre(){
		ChambreDao cDao = new ChambreDao(conn);
		HotelDao hDao = new HotelDao(conn);
		
		System.out.println("Quel est le nom de l'Hotel ou est la chambre ?");
		int idHotel = hDao.findByName(clavier.next()).getId();
		System.out.println("Quel est le numero de la chambre a supprimer ?");
		int num = clavier.nextInt();
		cDao.deleteByNum(num, idHotel);
	}

}

package Interface;

import java.io.Console;
import java.sql.Connection;
import java.sql.Date;
import java.util.Scanner;

import DAO.ChambreDao;
import DAO.HotelDao;
import DAO.LocationDao;
import Modele.Chambre;
import Modele.Hotel;
import Modele.Location;
import Modele.Service;
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
			System.out.println("Admin (1) ou Client(2)");
			rep=clavier.nextInt();
			switch(rep){
			case 1:	
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
			break;
			case 2:do{
				System.out.println("Que voulez vous faire ?");
				System.out.println("0 : Quitter");
				System.out.println("1 : Louer une chambre");
				System.out.println("2 : Rendre une chambre");
				System.out.println("3 : Modifier les services");
				System.out.println("4 : Modifier location");
				rep=clavier.nextInt();
				switch(rep){
				case 1:LouerChambre();
					break;
				case 2:RendreChambre();
					break;
				case 3:ModifierServices();
					break;
				case 4:ModifierLocation();
					break;
				}
			}while(rep!=0);
				break;
			}
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
	
	public static void LouerChambre(){
		Chambre c = new Chambre();
		ChambreDao cDao = new ChambreDao(conn);
		HotelDao hDao = new HotelDao(conn);
		String nom;
		Service serv;
		
		System.out.println("Quel est le nom de l'Hotel ou vous souhaitez resider ?");
		nom = clavier.next();
		Hotel h = hDao.findByName(nom);
		if(h!=null){
			System.out.println("Quel type de chambre souhaitez-vous ?\n(1: Simple;2:Double;3:Suite)");
			int type = clavier.nextInt();
			int idChambre=cDao.findChambreLibre(nom, type);
			if(idChambre==-1)
				System.out.println("Aucune chambre libre de ce type dans cet Hotel");
			else{
				Service s = SelectionService();
				SelectionDate(idChambre);
				c=cDao.find(idChambre);
				cDao.louer(idChambre, s.getId());
				System.out.println("Votre chambre est la n°"+c.getNumero()+" de l'Hotel "+nom+".");
			}
		}
		else{
			System.out.println("Aucun Hotel ne porte ce nom.");
		}
	}
	
	public static Service SelectionService(){
		Service s= new Service();
		int rep;
		
		System.out.println("Souhaitez vous le Wifi ?\n(1:Oui;2:Non)");
		rep = clavier.nextInt();
		if(rep==1)
			s.setWifi(true);
		
		System.out.println("Souhaitez-vous le room-Service ?");
		rep=clavier.nextInt();
		if(rep==1)
			s.setRoomService(true);
		
		System.out.println("Souhaitez-vous la tele ?");
		rep=clavier.nextInt();
		if(rep==1)
			s.setTele(true);
		
		System.out.println("Shouaitez-vous avoir un frigo rempli ?");
		rep=clavier.nextInt();
		if(rep==1)
			s.setFrigoRempli(true);
		
		return s;
	}
	
	public static void RendreChambre(){
		Chambre c = new Chambre();
		ChambreDao cDao = new ChambreDao(conn);
		HotelDao hDao = new HotelDao(conn);
		
		System.out.println("Quel est le nom de votre Hotel ?");
		String nom = clavier.next();
		System.out.println("Quel est le numero de votre chambre ?");
		int num = clavier.nextInt();
		cDao.rendreChambre(num, nom);		
	}
	
	public static void ModifierServices(){
		Chambre c = new Chambre();
		ChambreDao cDao = new ChambreDao(conn);
		HotelDao hDao = new HotelDao(conn);
		
		System.out.println("Quel est le nom de votre Hotel ?");
		String nom = clavier.next();
		System.out.println("Quel est le numéro de votre chambre ?");
		int num = clavier.nextInt();
		Service s = SelectionService();
		int service = s.getId();
		cDao.modifierService(num, nom, service);		
	}
	
	
	public static void SelectionDate(int idChambre){
		Location loc = new Location();
		LocationDao l = new LocationDao(conn);
		
		System.out.println("Quel jour arriverez-vous ?");
		int jour = clavier.nextInt();
		System.out.println("Quel mois ?");
		int mois = clavier.nextInt();
		System.out.println("Quelle année ?");
		int annee = clavier.nextInt();
		
		Date dateDebut = new Date(annee,mois,jour);
		
		System.out.println("Quel jour repartire vous ?");
		jour = clavier.nextInt();
		System.out.println("Quel mois ?");
		mois = clavier.nextInt();
		System.out.println("Quelle annee ?");
		annee = clavier.nextInt();
		
		Date dateFin = new Date(annee,mois,jour);
		
		loc.setDateDebut(dateDebut);
		loc.setDateRendu(dateFin);
		loc.setIdChambre(idChambre);
		
		l.create(loc);
	}
	
	public static void ModifierLocation(){
		Location loc = new Location();
		LocationDao l = new LocationDao(conn);
		HotelDao hDao = null;
		ChambreDao cDao = null;
		
		System.out.println("Quel est le nom de votre Hotel ?");		
		int idHotel = hDao.findByName(clavier.next()).getId();
		System.out.println("Quel est le numero de votre chambre ?");
		int num = clavier.nextInt();
		int idChambre = cDao.find(num, idHotel).getId();
		
		System.out.println("Quel jour repartire vous ?");
		int jour = clavier.nextInt();
		System.out.println("Quel mois ?");
		int mois = clavier.nextInt();
		System.out.println("Quelle annee ?");
		int annee = clavier.nextInt();
		
		Date dateFin = new Date(annee,mois,jour);
		
		loc.setDateRendu(dateFin);
		loc.setIdChambre(idChambre);
		l.update(loc);
		
	}
	
}

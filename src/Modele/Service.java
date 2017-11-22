package Modele;

public class Service {
	
	private int id;
	private boolean Wifi;
	private boolean Tele;
	private boolean RoomService;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isWifi() {
		return Wifi;
	}
	public void setWifi(boolean wifi) {
		Wifi = wifi;
	}
	public boolean isTele() {
		return Tele;
	}
	public void setTele(boolean tele) {
		Tele = tele;
	}
	public boolean isRoomService() {
		return RoomService;
	}
	public void setRoomService(boolean roomService) {
		RoomService = roomService;
	}
	public boolean isFrigoRempli() {
		return FrigoRempli;
	}
	public void setFrigoRempli(boolean frigoRempli) {
		FrigoRempli = frigoRempli;
	}

	private boolean FrigoRempli;
	
	
	
}

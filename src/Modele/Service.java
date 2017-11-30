package Modele;

public class Service {
	
	private int id;
	private boolean Wifi;
	private boolean Tele;
	private boolean RoomService;
	private boolean FrigoRempli;
	
	public Service(){
		this.FrigoRempli=this.RoomService=this.Tele=this.Wifi=false;
	}
	
	public int getId() {
		if(this.Wifi && this.FrigoRempli && this.RoomService && this.Tele)
			setId(1);
		if(this.Wifi && !this.FrigoRempli && this.RoomService && this.Tele)
			setId(2);
		if(this.Wifi && this.FrigoRempli && !this.RoomService && this.Tele)
			setId(3);
		if(this.Wifi && this.FrigoRempli && this.RoomService && !this.Tele)
			setId(4);
		if(!this.Wifi && this.FrigoRempli && this.RoomService && this.Tele)
			setId(5);
		if(!this.Tele && !this.Wifi && this.FrigoRempli && this.RoomService)
			setId(6);
		if(!this.Wifi && !this.RoomService && this.FrigoRempli && this.Tele)
			setId(7);
		if(!this.Wifi && !this.FrigoRempli && this.RoomService && this.Tele)
			setId(8);
		if(!this.Tele && !this.RoomService && this.Wifi && this.FrigoRempli)
			setId(9);
		if(!this.Tele && this.RoomService && this.Wifi && !this.FrigoRempli)
			setId(10);
		if(this.Wifi && !this.FrigoRempli && !this.RoomService && this.Tele)
			setId(11);
		if(this.Wifi && !this.FrigoRempli && !this.RoomService && !this.Tele)
			setId(12);
		if(!this.Wifi && !this.FrigoRempli && !this.RoomService && this.Tele)
			setId(13);
		if(!this.Wifi && !this.FrigoRempli && this.RoomService && !this.Tele)
			setId(14);
		if(!this.Wifi && this.FrigoRempli && !this.RoomService && !this.Tele)
			setId(15);
		if(!this.Wifi && !this.FrigoRempli && !this.RoomService && !this.Tele)
			setId(16);
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

	
	
	
}

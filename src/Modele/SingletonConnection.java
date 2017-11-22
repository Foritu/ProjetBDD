package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
	
	private static String url = "jdbc:postgresql://rhodes.ima.uco.fr:5432/cguerin";
	private static String user = "postgres";
	private static String password ="";
	
	private static Connection conn;
	
	public static Connection getConnection(){
		if(conn==null){
			try{
				conn = DriverManager.getConnection(url,user,password);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return conn;
	}
}

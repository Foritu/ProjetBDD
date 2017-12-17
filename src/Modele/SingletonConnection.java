package Modele;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
	
	private static String url = "jdbc:postgresql://rhodes.ima.uco.fr:5432/";
	private static String user = "postgres";
	private static String password ="";
	
	private static Connection conn;
	
	public static Connection getConnection(String str,String pw){
		if(conn==null){
			try{
				conn = DriverManager.getConnection(url+str,user,pw);
			}
			catch(SQLException e){
				e.printStackTrace();
			}
		}
		return conn;
	}
}

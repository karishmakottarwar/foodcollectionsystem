
package fc.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class ConnectionProvider {
	private static Connection con=null;
	public static Connection getConnection() {	
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File("/foodorderingsystemresources.properties")));
			if(con==null){
				Class.forName(properties.getProperty("driver"));
				con=DriverManager.getConnection(properties.getProperty("url"), properties.getProperty("username"), properties.getProperty("password"));
			}
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("Into connection provider catch");			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				//con=DriverManager.getConnection("jdbc:mysql://localhost:3306/FCS_STAGE", "root", "root");
				con=DriverManager.getConnection("jdbc:mysql://208.91.198.132:3306/bhava8nw_root", "bhava8nw_root", "scoe_1234");
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}			
		}
		System.out.println("New connection ::"+con);
		return con;
	} 
}

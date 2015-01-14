package rueckwaertssalto;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/*
 * 
 * Aufbau einer Connection zu einer RDBMS und 
 * deren Verwaltung
 * @author Nenad Gligorevic
 * @author Melanie Goebel
 * @version 20140114
 * 
 */
public class Connection {
	private java.sql.Connection con;
	private MysqlDataSource ds;
	
	/**
	 * 
	 * @param hostname Adresse des Hosts
	 * @param user Name des Users
	 * @param password Passwort des Users
	 * 
     * Methode kümmert sich um die Verbindung mit der Datenbank
	 * Falls Host, DB, Pass oder DB name nicht angegeben werden
	 * so wird dem entsprechend darauf mit Exception und Fehlermeldeungen
	 * reagiert
	 */
	public void connect (String hostname,String user,String password){
		ds = new MysqlDataSource();
		ds.setServerName(hostname);
		ds.setUser(user);
		ds.setPassword(password);
		try {
			this.con = ds.getConnection();
		} catch (SQLException e) {
			System.err.println("Failed to connect to "+hostname);
		}
	}
	/**
	 * Schliesst die Verbindung zur Datenbank
	 */
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Failed to close connection to "+ds.getServerName());
		}
	}
	/**
	 * 
	 * @param database die Datenbank
	 * @return Tabellen der Datenbank
	 * 
	 * Tabellen werden in ArrayList gespeichert 
	 * und ausgelesen. 
	 */
	public ArrayList<String> getTables(String database){
		Statement st;
		ArrayList<String> tables = new ArrayList<String>();
		try {
			st = con.createStatement();
			ResultSet rs1 = st.executeQuery("use "+database+";");
			ResultSet rs = st.executeQuery("show tables;");
			while(rs.next()){
				tables.add(rs.getString(1));
			}
		} catch (SQLException e) {
			System.err.println("Failed to send command. Is "+database+" really a database?");
		}
		return tables;
	}
	
}
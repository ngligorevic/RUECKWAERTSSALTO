package rueckwaertssalto;

import java.sql.DatabaseMetaData;
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
	private String database;
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
	 * Tabellen werden in ArrayList ausgelesen und gespeichert 
	 */
	public ArrayList<Tabelle> getTables(String database){
		this.database = database;
		Statement st;
		ArrayList<Tabelle> tables = new ArrayList<Tabelle>();
		try {
			st = con.createStatement();
			ResultSet rs1 = st.executeQuery("use "+database+";");
			ResultSet rs = st.executeQuery("show tables;");
			while(rs.next()){
				tables.add(new Tabelle(rs.getString(1)));
			}
		} catch (SQLException e) {
			System.err.println("Failed to send command. Is "+database+" really a database?");
		}
		return tables;
	}
	/**
	 * @param tabellen die Tabellen der DB
	 * 
	 * Informationen wie Attribute und Keys werden zu den Tabellen gespeichert
	 */
	public void getInfo(Tabelle t){
		if(database != null){
		Statement st;
		try{
			st = con.createStatement();
			ResultSet rs1 = st.executeQuery("use "+database+";");
			ResultSet rs = st.executeQuery("desc "+t.getName()+";");
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rsK = meta.getImportedKeys(database, null, t.getName());
			while(rs.next()){
				t.addAttribut(rs.getString(1));
				if(rs.getString(4).equals("PRI"))
					t.addPrimarykey(rs.getString(1));		
			}
			while(rsK.next()){
				t.addForeignkey(rsK.getString("FKCOLUMN_NAME"),rsK.getString("PKTABLE_NAME"));
			}
		}catch (SQLException e){
			System.err.println("Failed to send command. Is "+database+" really a database?");
		}
		}else{
			System.err.println("Please set the Database!");
		}
	}
	public void setDatabase(String database){
		this.database = database;
	}
	
	
}
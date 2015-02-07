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
	public void connect (String hostname,String user,String password, String database){
		ds = new MysqlDataSource();
		ds.setServerName(hostname);
		ds.setUser(user);
		ds.setPassword(password);
		ds.setDatabaseName(database);
		this.database = database;
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
	public ArrayList<Tabelle> getTables(){
		Statement st;
		ArrayList<Tabelle> tables = new ArrayList<Tabelle>();
		try {
			st = con.createStatement();
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

		Statement st;
		try{
			st = con.createStatement();
			ResultSet rs = st.executeQuery("desc "+t.getName()+";" );
			DatabaseMetaData meta = con.getMetaData();
			ResultSet rsK = meta.getImportedKeys(database, null, t.getName());
			Attribut a = null;
			Attribut b = null;
			while(rs.next()){
				if(rs.getString("Key").equals(""))
					t.addAttribut(new CommonAttribut(rs.getString(1)));
				else if(rs.getString("Key").equals("PRI"))
					t.addAttribut(new PrimaryKey(new CommonAttribut(rs.getString(1))));
				
				
			}
			while(rsK.next()){
	
				a = t.getAttributWithName(rsK.getString(8));
				if( a != null)
					t.setAttributWidthName(new ForeignKey(a, rsK.getString(3), rsK.getString(4)));
				else
					t.addAttribut(new ForeignKey(new CommonAttribut(rsK.getString(8)),  rsK.getString(3), rsK.getString(4)));
			}

		}catch (SQLException e){
			System.err.println("Failed to send command. Is "+database+" really a database?");
			e.printStackTrace();
		}
	}
	public void setDatabase(String database){
		this.database = database;
	}


}
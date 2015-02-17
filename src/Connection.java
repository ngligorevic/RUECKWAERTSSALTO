package rueckwaertssalto;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
	private ArrayList<Tabelle> tabellen = new ArrayList<Tabelle>();
	private HashMap<String,Relationship> relationships = new HashMap<String,Relationship>(); 

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
			this.updateTables();
		} catch (SQLException e) {
			System.err.println("Failed to connect to "+hostname);
			System.exit(1);
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
	public ArrayList<Tabelle> getTables(){
		return this.tabellen;
	}
	public HashMap<String, Relationship> getRelationship(){
		return this.relationships;
	}
	/**
	 * 
	 * @param database die Datenbank
	 * @return Tabellen der Datenbank
	 * 
	 * Tabellen werden in ArrayList ausgelesen und gespeichert 
	 */
	public ArrayList<Tabelle> updateTables(){
		Statement st;
		tabellen = new ArrayList<Tabelle>();
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery("show tables;");
			while(rs.next()){
				tabellen.add(new Tabelle(rs.getString(1)));
			}
		} catch (SQLException e) {
			System.err.println("Failed to send command. Is "+database+" really a database?");
		}
		
		getInfo();
		return tabellen;
	}

	/**
	 * @param tabellen die Tabellen der DB
	 * 
	 * Informationen wie Attribute und Keys werden zu den Tabellen gespeichert
	 */
	private void getInfo(){
		for(int i = 0; i < tabellen.size(); i++){
			Statement st;
			try{
				st = con.createStatement();
				ResultSet rs = st.executeQuery("desc "+tabellen.get(i).getName()+";" );
				DatabaseMetaData meta = con.getMetaData();
				ResultSet rsK = meta.getImportedKeys(database, null, tabellen.get(i).getName());
				Attribut a = null;
				Attribut b = null;
				while(rs.next()){
					if(rs.getString("Key").equals("")){
						if(rs.getString("Null").equals("No"))
							tabellen.get(i).addAttribut(new CommonAttribut(rs.getString(1), tabellen.get(i).getName(), true));
						else
							tabellen.get(i).addAttribut(new CommonAttribut(rs.getString(1), tabellen.get(i).getName(), false));
					}else if(rs.getString("Key").equals("PRI"))
						if(rs.getString("Null").equals("No"))
							tabellen.get(i).addAttribut(new PrimaryKey(new CommonAttribut(rs.getString(1), tabellen.get(i).getName(),  true)));
						else
							tabellen.get(i).addAttribut(new PrimaryKey(new CommonAttribut(rs.getString(1), tabellen.get(i).getName(),  false)));


				}
				while(rsK.next()){
					a = tabellen.get(i).getAttributWithName(rsK.getString(8));
					if( a != null)
						tabellen.get(i).setAttributWidthName(new ForeignKey(a, rsK.getString(3), rsK.getString(4)));
					else
						//					if(rs.getString("Null").equals("No"))
						tabellen.get(i).addAttribut(new ForeignKey(new CommonAttribut(rsK.getString(8), tabellen.get(i).getName(),true),  rsK.getString(3), rsK.getString(4)));
					//					else
					//						t.addAttribut(new ForeignKey(new CommonAttribut(rsK.getString(8), t.getName(),false),  rsK.getString(3), rsK.getString(4)));	
					
						relationships.put(tabellen.get(i).getName()+rsK.getString(3),new Relationship(tabellen.get(i).getName(),rsK.getString(3)));
						
				}

			}catch (SQLException e){
				System.err.println("Failed to send command. Is "+database+" really a database?");
				e.printStackTrace();
			}
		}

	}
	public boolean hasRelationship(String tabelle1, String tabelle2){
		for(int i = 0; i < this.relationships.size(); i++){
			if(relationships.get(i).getNameTabelle1().equals(tabelle1)){
				if(relationships.get(i).getNameTabelle2().equals(tabelle2)){
					return true;
				}
			}else if(relationships.get(i).getNameTabelle1().equals(tabelle2)){
				if(relationships.get(i).getNameTabelle2().equals(tabelle1)){
					return true;
				}
			}
		}
		return false;
	}


}
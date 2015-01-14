package rueckwaertssalto;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class Connection {
	private java.sql.Connection con;
	private MysqlDataSource ds;
	
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
	public void close(){
		try {
			con.close();
		} catch (SQLException e) {
			System.err.println("Failed to close connection to "+ds.getServerName());
		}
	}
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
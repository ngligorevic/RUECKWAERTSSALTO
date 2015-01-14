package rueckwaertssalto;

import java.util.ArrayList;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect("10.0.0.8", "insy4", "blabla");
		ArrayList<Tabelle> tabellen = con.getTables("raumverwaltung");
		for(int i = 0; i < tabellen.size(); i++){
			System.out.println(tabellen.get(i).getName());
			con.getInfo(tabellen.get(i));
		}
	}
}

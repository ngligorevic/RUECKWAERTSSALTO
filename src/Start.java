package rueckwaertssalto;

import java.util.ArrayList;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect("172.16.0.10", "insy4", "blabla");
		Diagram d = new Diagram();
		System.out.println(d.getRM(con, "testinsy"));
		
	}
}

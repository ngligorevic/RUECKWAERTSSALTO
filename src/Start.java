package rueckwaertssalto;

import java.util.ArrayList;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect("10.0.0.8", "insy4", "blabla");
		Diagram d = new Diagram();
		System.out.println(d.getRM(con, "testinsy"));
		
	}
}

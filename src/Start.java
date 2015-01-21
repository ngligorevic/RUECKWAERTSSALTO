package rueckwaertssalto;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect("10.0.105.60", "insy4", "blabla");
		Diagram d = new Diagram();
		System.out.println(d.getRM(con, "testinsy","rm.txt"));
		
	}
}

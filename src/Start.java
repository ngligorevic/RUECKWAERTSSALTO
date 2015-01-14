package rueckwaertssalto;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		con.connect("10.0.104.172", "insy4", "blabla");
		System.out.println(con.getTables("premiere"));
	}
}

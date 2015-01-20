package rueckwaertssalto;

import java.util.ArrayList;

public class Start {
	public static void main(String[] args) {
		Connection con = new Connection();
		ArrayList<String> atr;
		ArrayList<String> pk;
		ArrayList<String> fk;
		con.connect("172.16.0.9", "insy4", "blabla");
		ArrayList<Tabelle> tabellen = con.getTables("testinsy");
		for(int i = 0; i < tabellen.size(); i++){
			System.out.print(tabellen.get(i).getName()+"(");
			con.getInfo(tabellen.get(i));
			atr = tabellen.get(i).getAttributs();
			pk = tabellen.get(i).getPrimarykeys();
			fk = tabellen.get(i).getForeignkeys();
			for(int j = 0; j < atr.size(); j++){
				if(pk.contains(atr.get(j))){
					System.out.print("<PK>:"+atr.get(j)+" , ");
				}else{
					System.out.print(atr.get(j)+" , ");
				}
			}
			System.out.println(")");
		}
	}
}

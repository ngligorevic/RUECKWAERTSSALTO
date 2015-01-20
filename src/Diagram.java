package rueckwaertssalto;

import java.util.ArrayList;
/**
 * @author Melanie Goebel
 * @version 20140120
 * Erstellt Diagramme und Modelle zur Datanbank
 */
public class Diagram{

	public String getRM(Connection con, String database){
		String ausg = "";
		ArrayList<String> atr;
		ArrayList<String> pk;
		ArrayList<String> fk;
		ArrayList<Tabelle> tabellen = con.getTables(database);
		for(int i = 0; i < tabellen.size(); i++){
			ausg += tabellen.get(i).getName()+"(";
			con.getInfo(tabellen.get(i));
			atr = tabellen.get(i).getAttributs();
			pk = tabellen.get(i).getPrimarykeys();
			fk = tabellen.get(i).getForeignkeys();
			for(int j = 0; j < atr.size(); j++){
				if(fk.contains(atr.get(j)))
					ausg += "<FK>";
				if(pk.contains(atr.get(j)))
					ausg += "<PK>";
				ausg += atr.get(j)+" , ";
			}
			ausg += ")\n";
		}
		return ausg;
	}
}
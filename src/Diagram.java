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
		ArrayList<ForeignKey> fk;
		ArrayList<Tabelle> tabellen = con.getTables(database);
		for(int i = 0; i < tabellen.size(); i++){
			ausg += tabellen.get(i).getName()+"(";
			con.getInfo(tabellen.get(i));
			atr = tabellen.get(i).getAttributs();
			pk = tabellen.get(i).getPrimarykeys();
			fk = tabellen.get(i).getForeignkeys();
			for(int j = 0; j < atr.size(); j++){
				if(pk.contains(atr.get(j)))
					ausg += "<PK>";
				String table = getFKTable(fk,atr.get(j));
				if(table != null)
					ausg += "<FK>"+table+".";
				ausg += atr.get(j);
				if(j < atr.size()-1)
					ausg += ", ";
			}
			ausg += ")\n";
		}
		return ausg;
	}
	private String getFKTable(ArrayList<ForeignKey> fk, String key){
		for(int i=0; i < fk.size(); i++){
			if(fk.get(i).equals(key))
				return fk.get(i).getForeigntable();
		}
		return null;
	}
}
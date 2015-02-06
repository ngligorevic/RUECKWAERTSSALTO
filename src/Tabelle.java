package rueckwaertssalto;

import java.util.*;

/**
 * @author Nenad Gligorevic
 * @author Melanie Goebel
 * @version 20140114
 * Speichert und verwaltet Informationen einer Tabelle
 */
public class Tabelle {
	private ArrayList<Attribut> attribute;
	private String name;
	
	/**
	 * Gibt der Tabelle einen Namen und initalisiert die Listen
	 * @param name
	 */
	public Tabelle(String name) {
		this.name = name;
		this.attribute = new ArrayList<Attribut>();
	}
	/**
	 * Gibt den Namen der Tabelle als Text zurueck
	 * @return den Namen
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Hinzufuegen eines Attributs
	 * @param attribut den Namen des Attributs
	 */
	public void addAttribut(Attribut a) {
		this.attribute.add(a);
	}
	
	/**
	 * Gibt die Liste der Attribute zurueck
	 * @return die Attribute in einemn ArrayList
	 */
	public ArrayList<Attribut> getAttributs(){
		return attribute;
	}
	public String getRM(){
		String rm = name+"( ";
		for(int i = 0; i < attribute.size(); i++){
			rm+=attribute.get(i).getRMText();
			if(i < attribute.size()-1)
				rm+=",";
		}
		rm += " )";
		return rm;
	}
}

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
	private ArrayList<String> primarykey;
	private ArrayList<ForeignKey> foreignkey;
	private String name;
	
	/**
	 * Gibt der Tabelle einen Namen und initalisiert die Listen
	 * @param name
	 */
	public Tabelle(String name) {
		this.name = name;
		this.attribute = new ArrayList<Attribut>();
		this.primarykey = new ArrayList<String>();
		this.foreignkey = new ArrayList<ForeignKey>();
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
	public void addAttribut(String name) {
		this.attribute.add(new CommonAttribut(name));
	}
	/**
	 * Hinzufuegen eines PrimaryKeys
	 * @param key den Namen des Primarykeys
	 */
	public void addPrimarykey(String key) {
		this.primarykey.add(key);
	}
	/**
	 * Hinzufuegen eines Foreignkeys
	 * @param key den Namen des Foreignkeys
	 */
	public void addForeignkey(String key, String table) {
		this.foreignkey.add(new ForeignKey(key,table));
	}
	/**
	 * Gibt die Liste der Attribute zurueck
	 * @return die Attribute in einemn ArrayList
	 */
	public ArrayList<Attribut> getAttributs(){
		return attribute;
	}
	/**
	 * Gibt die Liste der PrimayKeys zurueck
	 * @return die PrimaryKeys in einemn ArrayList
	 */
	public ArrayList<String> getPrimarykeys(){
		return primarykey;
	}
	/**
	 * Gibt die Liste der ForeignKeys zurueck
	 * @return die ForeignKeys in einemn ArrayList
	 */
	public ArrayList<ForeignKey> getForeignkeys(){
		return foreignkey;
	}

}

package rueckwaertssalto;

import java.util.*;

/**
 * 
 * @author Nenad Gligorevic
 * @author Melanie Goebel
 * @version 20140114
 * 
 * 
 */
public class Tabelle {
	private ArrayList<String> attribute;
	private ArrayList<String> primarykey;
	private ArrayList<String> foreignkey;
	private String name;
	
	public Tabelle(String name) {
		this.name = name;
		this.attribute = new ArrayList<String>();
		this.primarykey = new ArrayList<String>();
		this.foreignkey = new ArrayList<String>();
	}
	
	public String getName() {
		return this.name;
	}
	
	public void addAttributekey(String attribute) {
		this.attribute.add(attribute);
	}
	public void addPrimarykey(String key) {
		this.primarykey.add(key);
	}
	public void addForeignkey(String key) {
		this.foreignkey.add(key);
	}

}

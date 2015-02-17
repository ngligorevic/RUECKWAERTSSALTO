package rueckwaertssalto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Melanie Goebel
 * @version 20140120
 * Erstellt Diagramme und Modelle zur Datanbank
 */
public class Diagram{

	public boolean getRM(Connection con, String database, String filename){
		File file;
		FileWriter rf;
		BufferedWriter bf;
		ArrayList<Tabelle> tabellen = con.updateTables();
		try{
			file = new File(filename);
			rf = new FileWriter(file);
			bf = new BufferedWriter(rf); 
			for(int i = 0; i < tabellen.size(); i++){
				bf.write(tabellen.get(i).getRM());
				bf.newLine();//Zeilenumbruch
			}
			bf.close();rf.close();//Schließen damit man es nochmal verwenden kann.
		} catch (FileNotFoundException ex) {
			System.err.println("File "+filename+" cannot be created");
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Failed to write in "+filename);
			return false;
		}
		return true;
	}
	
	public boolean getDotFile(Connection con, String database, String filename){
		HashMap<String,Relationship> relationships = con.getRelationship();
		File file;
		FileWriter rf;
		BufferedWriter bf;
		try{
			file = new File(filename);
			rf = new FileWriter(file);
			bf = new BufferedWriter(rf); 
			ArrayList<Attribut> atr;
			ArrayList<Tabelle> tabellen = con.updateTables();
			bf.write("digraph "+database+" {");
			bf.newLine();
			for(int i = 0; i < tabellen.size(); i++){
				bf.write(tabellen.get(i).getName()+"[shape=box];");
				bf.newLine();
				atr = tabellen.get(i).getAttributs();
		
				for(int j = 0; j < atr.size(); j++){
					bf.write(atr.get(j).getERDText());
					bf.newLine();
				}
			}
			for(String key: relationships.keySet()){
				bf.write(relationships.get(key).getERDText());
				bf.newLine();
			}
			bf.write("}");
			bf.close();rf.close();//Schließen damit man es nochmal verwenden kann.
		} catch (FileNotFoundException ex) {
			System.err.println("File "+filename+" cannot be created");
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			System.err.println("Failed to write in "+filename);
			return false;
		}
		return true;
	}
	public boolean Drawpng(String dotfile, String file){
		Process proc;
		try {
			proc = Runtime.getRuntime().exec("neato -Tpng "+dotfile+" -o "+file);
			return true;

		} catch (IOException e) {
			System.err.println("Something went wront while generate "+file);
			return false;
		}


	}
}
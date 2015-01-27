package rueckwaertssalto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
		try{
			file = new File(filename);
			rf = new FileWriter(file);
			bf = new BufferedWriter(rf); 
			ArrayList<String> atr;
			ArrayList<String> pk;
			ArrayList<ForeignKey> fk;
			ArrayList<Tabelle> tabellen = con.getTables(database);
			for(int i = 0; i < tabellen.size(); i++){
				bf.write(tabellen.get(i).getName()+"(");
				con.getInfo(tabellen.get(i));
				atr = tabellen.get(i).getAttributs();
				pk = tabellen.get(i).getPrimarykeys();
				fk = tabellen.get(i).getForeignkeys();
				for(int j = 0; j < atr.size(); j++){
					if(pk.contains(atr.get(j)))
						bf.write("<PK>");
					String table = getFKTable(fk,atr.get(j));
					if(table != null)
						bf.write("<FK>"+table+".");
					bf.write( atr.get(j));
					if(j < atr.size()-1)
						bf.write(", ");
				}
				bf.write(")");
				bf.newLine();//Zeilenumbruch
			}
			bf.close();rf.close();//Schlieﬂen damit man es nochmal verwenden kann.
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
	private String getFKTable(ArrayList<ForeignKey> fk, String key){
		for(int i=0; i < fk.size(); i++){
			if(fk.get(i).equals(key))
				return fk.get(i).getForeigntable();
		}
		return null;
	}
	public boolean getDotFile(Connection con, String database, String filename){
		File file;
		FileWriter rf;
		BufferedWriter bf;
		try{
			file = new File(filename);
			rf = new FileWriter(file);
			bf = new BufferedWriter(rf); 
			String ftable = "";
			ArrayList<String> atr;
			ArrayList<String> pk;
			ArrayList<ForeignKey> fk;
			ArrayList<Tabelle> tabellen = con.getTables(database);
			bf.write("digraph "+database+" {");
			bf.newLine();
			for(int i = 0; i < tabellen.size(); i++){
				bf.write(tabellen.get(i).getName()+"[shape=box];");
				bf.newLine();
				con.getInfo(tabellen.get(i));
				atr = tabellen.get(i).getAttributs();
				pk = tabellen.get(i).getPrimarykeys();
				fk = tabellen.get(i).getForeignkeys();
				// raumrnr_PRI[shape=ellipse,style=filled,color=red,label="rnr"];
				for(int j = 0; j < atr.size(); j++){
					bf.write(tabellen.get(i).getName()+atr.get(j)+"[shape=ellipse,style=filled,");
					if(pk.contains(atr.get(j)))
						bf.write("color=red,");
					String table = getFKTable(fk,atr.get(j));
					bf.write("label =\""+atr.get(j)+"\"];");
					bf.newLine();
					bf.write(tabellen.get(i).getName()+"->"+tabellen.get(i).getName()+atr.get(j));
					bf.newLine();
					if(table != null){
						ftable = table+tabellen.get(i).getName()+"[shape=diamond,label=\"\"];"+table+"->"+table+tabellen.get(i).getName()+"->"+tabellen.get(i).getName();
					}
					//bf.write("<FK>"+table+".");
					
					//bf.write( atr.get(j));
				}
				//bf.write(")");
				//bf.write("];");
				bf.newLine();//Zeilenumbruch
				if(ftable.equals("")==false){
				bf.write(ftable+";");
				bf.newLine();
				ftable = "";
				}
			}
			bf.write("}");
			bf.close();rf.close();//Schlieﬂen damit man es nochmal verwenden kann.
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
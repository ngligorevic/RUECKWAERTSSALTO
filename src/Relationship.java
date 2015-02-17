package rueckwaertssalto;
/**
 * 
 * @author Melanie Goebel
 * @version 2015-02-17
 */
public class Relationship {
	private String tabelle1;
	private String tabelle2;
	private boolean t1schwach;
	private boolean t2schwach;
	
	public Relationship(String tabelle1, String tabelle2){
		this.tabelle1 = tabelle1;
		this.tabelle2 = tabelle2;
	}
	public String getNameTabelle1(){
		return tabelle1;
	}
	public String getNameTabelle2(){
		return tabelle2;
	}
	public String getERDText(){
		return tabelle1+tabelle2+"[shape=diamond,label=\"\"];"+
				tabelle1+"->"+tabelle1+tabelle2+"[arrowhead=none, label=\"1\"];"+
				tabelle1+tabelle2+"->"+tabelle2+"[arrowhead=none, label=\"n\"];";
	}
}

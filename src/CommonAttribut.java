package rueckwaertssalto;

public class CommonAttribut implements Attribut{
	String name;
	String table;
	
	public CommonAttribut(String name, String table){
		this.name = name;
		this.table = table;
	}

	@Override
	public String getName() {
		return this.name;
	}
	@Override
	public String getTable() {
		return this.table;
	}
	
	@Override
	public String getRMText() {
		return this.name;
	}
	@Override
	public String getERDText(){
		return  this.table+"->"+this.table+this.name+"[arrowhead=none];"+
				this.table+this.name+"[shape=ellipse,label =\""+this.name+"\"];";
	}
}

package rueckwaertssalto;

public class CommonAttribut implements Attribut{
	private String name;
	private String table;
	private boolean notNull;
	
	public CommonAttribut(String name, String table, boolean notNull){
		this.name = name;
		this.table = table;
		this.notNull = notNull;
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

package rueckwaertssalto;

public abstract class Decorator implements Attribut{
	private Attribut a;
	
	public Decorator(Attribut a){
		this.a = a;
	}

	public String getName(){
		return a.getName();
	}
	public String getTable(){
		return a.getTable();
	}
}

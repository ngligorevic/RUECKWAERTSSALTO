package rueckwaertssalto;

public abstract class Decorator implements Attribut{
	private Attribut a;
	
	public Decorator(Attribut a){
		this.a = a;
	}
	public String getRMText(){
		return a.toString();
	}
	public String getName(){
		return a.getName();
	}
}

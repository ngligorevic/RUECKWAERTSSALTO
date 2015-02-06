package rueckwaertssalto;

public class PrimaryKey extends Decorator{
	private Attribut a;
	
	public PrimaryKey(Attribut a) {
		super(a);
		this.a = a;
	}

	@Override
	public String getName() {
		return this.getName();
	}
	@Override
	public String getRMText(){
		return "<PK>"+a.getRMText();
	}
	
	
}

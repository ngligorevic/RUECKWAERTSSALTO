package rueckwaertssalto;

public class PrimaryKey extends Decorator{
	private Attribut a;
	private String name;
	
	public PrimaryKey(Attribut a) {
		super(a);
		this.a = a;
		name = a.getName();
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public String getRMText(){
		return a.getRMText()+"<PK>";
	}

	@Override
	public String getERDText() {
		String[] erd = a.getERDText().split(";");
		return erd[0]+";"+erd[1]+"[color=red];";
	}
	
	
}

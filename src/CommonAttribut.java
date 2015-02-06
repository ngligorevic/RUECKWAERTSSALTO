package rueckwaertssalto;

public class CommonAttribut implements Attribut{
	String name;
	
	public CommonAttribut(String name){
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getRMText() {
		return this.name;
	}
}

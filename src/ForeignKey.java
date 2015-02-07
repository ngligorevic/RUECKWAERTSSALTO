package rueckwaertssalto;

public class ForeignKey extends Decorator{
	private String name;
	private String foreigntable;
	private String nameForeignTable;
	private Attribut a;

	public ForeignKey(Attribut a, String foreigntable, String nameForeignTable) {
		super(a);
		this.a = a;
		this.foreigntable = foreigntable;
		this.nameForeignTable = nameForeignTable;
		this.name = a.getName();
	}
	@Override
	public String getRMText() {
		return foreigntable+"."+nameForeignTable+":"+a.getRMText();
	}
	  

//	public ForeignKey(String name, String table, String nameForeignTable){
//		this.nameThisTable = name;
//		this.foreigntable = table;
//		this.nameForeignTable = nameForeignTable;
//	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the foreigntable
	 */
	public String getForeigntable() {
		return foreigntable;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param foreigntable the foreigntable to set
	 */
	public void setForeigntable(String foreigntable) {
		this.foreigntable = foreigntable;
	}


}


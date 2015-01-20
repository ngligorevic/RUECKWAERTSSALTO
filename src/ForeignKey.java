package rueckwaertssalto;

public class ForeignKey{
	private String name;
	private String foreigntable;
	public ForeignKey(String name, String table){
		this.name = name;
		this.foreigntable = table;
	}
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
	@Override
	public boolean equals(Object o) {
		if(this.name.equals(o))
			return true;
		else
			return false;
	}
	@Override
	public String toString(){
		return foreigntable+"."+name;
	}

}


package uk.co.alt236.easycursor.sqlcursor.querybuilders;
public enum Order{
	ASC("ASC"),
	DESC("DESC");

	final private String order;

	private Order(String order){
		this.order = order;
	}

    @Override
    public String toString(){
        return order;
    }
}
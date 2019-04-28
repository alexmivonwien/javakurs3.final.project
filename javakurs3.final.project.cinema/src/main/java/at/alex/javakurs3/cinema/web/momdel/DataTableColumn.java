package at.alex.javakurs3.cinema.web.momdel;

/**
 * @author User
 * 
 * @see https://www.concretepage.com/jsf-2/primefaces-5/primefaces-5-datatable-with-dynamic-columns-example
 *
 */
public class DataTableColumn {
	
	private String header;
	private String property;

	public DataTableColumn(String header, String property) {
		this.header = header;
		this.property = property;
	}

	public String getHeader() {
		return header;
	}

	public String getProperty() {
		return property;
	}
}
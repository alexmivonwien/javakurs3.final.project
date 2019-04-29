package at.alex.javakurs3.cinema.web.momdel;

import java.io.Serializable;

/**
 * @author User
 * 
 * @see https://www.concretepage.com/jsf-2/primefaces-5/primefaces-5-datatable-with-dynamic-columns-example
 * @see https://www.primefaces.org/showcase/ui/data/datatable/columns.xhtml
 * 
 *
 */
public class DataTableColumn implements Serializable{

	private static final long serialVersionUID = 1L;

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
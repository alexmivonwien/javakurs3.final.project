package at.alex.javakurs3.cinema.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.SeatForShow;
import at.alex.javakurs3.cinema.web.momdel.DataTableColumn;

/**
 * 
 * @author User
 * @see https://www.concretepage.com/jsf-2/primefaces-5/primefaces-5-datatable-with-dynamic-columns-example
 * 
 */

@ManagedBean
@ViewScoped
public class CinemaSeatViewBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private List<SeatForShow> seatsForShowList = new ArrayList<SeatForShow>();
	private List<DataTableColumn> dataTableColumns = new ArrayList<DataTableColumn>();

	@PostConstruct
	public void init() {
		// add SeatForShows
		ExternalContext extContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Flash flash = extContext.getFlash();
		FilmShow selectedFilmShow = (FilmShow)flash.get(FilmAndCinemaChooserBean.SELECTED_FILM_SHOW);
		
		int noOfColumns = selectedFilmShow.getNumberOfColumns();
		
		for (int i = 0; i < noOfColumns; i ++){
			// prepare dynamic columns
			dataTableColumns.add(new DataTableColumn("Seat " + (i+1), "seatNo"));
		}
		
	}

	public void setDataTableColumns(List<DataTableColumn> dataTableColumns) {
		this.dataTableColumns = dataTableColumns;
	}

	public List<DataTableColumn> getDataTableColumns() {
		return dataTableColumns;
	}

	public List<SeatForShow> getSeatsForShowList() {
		return seatsForShowList;
	}

	public void setSeatsForShowList(List<SeatForShow> seatsForShowList) {
		this.seatsForShowList = seatsForShowList;
	}

}
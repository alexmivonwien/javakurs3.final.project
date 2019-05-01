package at.alex.javakurs3.cinema.web;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

import org.apache.commons.lang3.StringUtils;

import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.SeatForShow;

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
	private int noOfColumns;
	
	@PostConstruct
	public void init() {
		// add SeatForShows
		ExternalContext extContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Flash flash = extContext.getFlash();
		FilmShow selectedFilmShow = (FilmShow)flash.get(FilmAndCinemaChooserBean.SELECTED_FILM_SHOW + StringUtils.EMPTY);
		
		this.noOfColumns = selectedFilmShow.getNumberOfColumns();
		
		for ( SeatForShow seatForShow: selectedFilmShow.getSeatsForShow()){
			seatsForShowList.add(seatForShow);
		}
		
		Collections.sort(seatsForShowList);
		
	}


	public int getNoOfColumns() {
		return noOfColumns;
	}

	public int getNoOfSeatsInShow() {
		return this.seatsForShowList.size();
	}

	public List<SeatForShow> getSeatsForShowList() {
		return seatsForShowList;
	}

	public void setSeatsForShowList(List<SeatForShow> seatsForShowList) {
		this.seatsForShowList = seatsForShowList;
	}

}

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
import javax.faces.event.ActionEvent;

import org.apache.commons.lang3.StringUtils;

import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Reservation;
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
	private FilmShow selectedFilmShow;
	
	@PostConstruct
	public void init() {
		// add SeatForShows
		ExternalContext extContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Flash flash = extContext.getFlash();
		this.selectedFilmShow = (FilmShow)flash.get(FilmAndCinemaChooserBean.SELECTED_FILM_SHOW + StringUtils.EMPTY);
		
		this.noOfColumns = selectedFilmShow.getNumberOfColumns();
		
		for ( SeatForShow seatForShow: selectedFilmShow.getSeatsForShow()){
			seatsForShowList.add(seatForShow);
		}
		
		Collections.sort(seatsForShowList);
		
	}

	public void reserveSeat(SeatForShow seatForShow){
		
		Reservation reservation = new Reservation();
		reservation.setCustomer(null);
		reservation.setFilmShow(this.selectedFilmShow);
		reservation.getSeatsReserved().add(seatForShow);
		seatForShow.setReservation(reservation);
	}

	public void seatSelected (ActionEvent e){
		Object obj = e.getSource();
		System.out.println(obj);
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

package at.alex.javakurs3.cinema.web;

import java.io.Serializable;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Reservation;
import at.alex.javakurs3.cinema.model.SeatForShow;
import at.alex.javakurs3.cinema.service.ReservationService;

/**
 * 
 * @author Alex-Mi
 * 
 * Unless you're using JSF 2.2 (which is still not out yet at this moment) or
 * MyFaces CODI (which I'd have expected that you would explicitly mention that)
 * the @ViewScoped doesn't work in CDI. This also pretty much matches your
 * problem symptoms.
 * 
 * http://stackoverflow.com/questions/14812238/jsf-view-scoped-bean-
 * reconstructed-multiple-times
 * 
 * 
 * As per JSF 2.2 and higher, @ManagedBean is deprecated. Use @Named together with @javax.faces.view.ViewScoped,
 * @see https://stackoverflow.com/a/4347707/1925356
 * @see @javax.faces.view.ViewScoped documentation:  
 * 
 * When this annotation, along with javax.inject.Named is found on a class, the runtime must place
 * the bean in a CDI scope such that it remains active as long as javax.faces.application.NavigationHandler.handleNavigation 
 * does not cause a navigation to a view with a viewId that is different than theview Id of the current view. Any injections and
 * notifications required by CDI and the Java EE platform must occur as usual at the expected time.
 * 
 * 
 */

@Named
@javax.faces.view.ViewScoped
// javax.faces.bean.ViewScoped is deprecated as per JSF 2.2
// javax.faces.bean.ManagedBean is deprecated as per JSF 2.2
public class CinemaSeatViewBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<SeatForShow> seatsForShowList = new ArrayList<SeatForShow>();
	private int noOfColumns;
	private FilmShow selectedFilmShow;
	private Reservation reservationCreated;
	
	@Inject
	private ReservationService reservationService;
	
	@PostConstruct
	public void init() {
		// add SeatForShows
		ExternalContext extContext = FacesContext.getCurrentInstance()
				.getExternalContext();

		Flash flash = extContext.getFlash();
		this.selectedFilmShow = (FilmShow)flash.get(FilmAndCinemaChooserBean.SELECTED_FILM_SHOW);
		
		this.noOfColumns = selectedFilmShow.getNumberOfColumns();
		
		for ( SeatForShow seatForShow: selectedFilmShow.getSeatsForShow()){
			seatsForShowList.add(seatForShow);
		}
		
		Collections.sort(seatsForShowList);
		
	}

	public void reserveSeat(String seatReserved){
		
		byte rowNo = Byte.parseByte(seatReserved.substring(0, seatReserved.indexOf('/')));
		byte seatNo = Byte.parseByte(seatReserved.substring(seatReserved.indexOf('/') + 1));
		
		SeatForShow selectedSeatForShow = null;
		for (SeatForShow seatForShow : seatsForShowList) {
			if (seatForShow.getRowNo() == rowNo && seatForShow.getSeatNo() == seatNo) {
				selectedSeatForShow = seatForShow;
				break;
			}
		}
		
		if (this.reservationCreated == null){
			this.reservationCreated = new Reservation();
			this.reservationCreated.setCustomer(null);
			this.reservationCreated.setFilmShow(selectedFilmShow);
		} 
		
		selectedSeatForShow.setReservation(this.reservationCreated);
		this.reservationCreated.setTotalPrice(this.reservationCreated.getTotalPrice().add(selectedSeatForShow.getPrice()));
		this.reservationCreated.getSeatsReserved().add(selectedSeatForShow);
		
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
	
	public String next(){
		if (this.reservationCreated!=null){
			this.reservationService.saveReservation(this.reservationCreated);
		}
		return "selectFilmCinema?faces-redirect=true";
		
	}

}

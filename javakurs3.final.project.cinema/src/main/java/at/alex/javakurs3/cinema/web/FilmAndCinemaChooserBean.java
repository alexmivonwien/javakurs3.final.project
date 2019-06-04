package at.alex.javakurs3.cinema.web;


import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.service.FilmService;

/**
 * 
 * @author Alex-Mi
 * 
 * @see https://stackoverflow.com/questions/16559559/disable-specific-dates-on-pcalendar
 * @see https://zenidas.wordpress.com/recipes/primefaces-calendar-customization/
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
public class FilmAndCinemaChooserBean implements Serializable {
	
	public static final String SELECTED_FILM_SHOW = "selectedFilmShow";

	@Inject
	private FilmService filmService;

	private List<FilmShow> futureFilmShows;

	private int selectedCinema;
	private int selectedFilm;
	private Date selectedDate;

	private Map<Integer, String> availableCinemas = new HashMap<>();
	private Map<Integer, String> availableFilms = new HashMap<>();
	private Set<Date> availableDates = new HashSet<Date>();

	@PostConstruct
	private void init() {
		futureFilmShows = filmService.findFilmShows(StringUtils.EMPTY);

		for (FilmShow filmShow : futureFilmShows) {
			availableCinemas.put(filmShow.getCinema().getId(), filmShow.getCinema().toString());
			availableFilms.put(filmShow.getFilm().getId(), filmShow.getFilm().toString());
			availableDates.add(filmShow.getBeginning());
		}
	}

	public void updateAvailableDates() {
		availableDates.clear();
		for (FilmShow filmShow: futureFilmShows){
			int filmShowCinemaId = filmShow.getCinema().getId();
			int filmShowFilmId = filmShow.getFilm().getId();
			
			if ( 	(this.selectedFilm <=   0 || this.selectedFilm == filmShowFilmId) 
				 && (this.selectedCinema <= 0 || this.selectedCinema == filmShowCinemaId)
					){
				availableDates.add(filmShow.getBeginning());
			}
		}
	}
	
	public void setAvailableDatesAsString(String datesAsString){
		
	}
	public String getAvailableDatesAsString() {
		if (availableDates.size() == 0) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StringBuilder sb = new StringBuilder(StringUtils.EMPTY);
		
		for (Date date : availableDates) {
			sb.append(sdf.format(date) +",");
		}
		return sb.toString();
	}

	public Date getSelectedDate() {
		return selectedDate;
	}

	public void setSelectedDate(Date selectedDate) {
		this.selectedDate = selectedDate;
	}

	public int getSelectedCinema() {
		return selectedCinema;
	}

	public void setSelectedCinema(int selectedCinema) {
		this.selectedCinema = selectedCinema;
	}

	public int getSelectedFilm() {
		return selectedFilm;
	}

	public void setSelectedFilm(int selectedFilm) {
		this.selectedFilm = selectedFilm;
	}

	public Map<Integer, String> getAvailableCinemas() {
		if (this.selectedFilm != 0) { // a film is selected, filter all
										// availableCinemas
			availableCinemas.clear();
			availableDates.clear();

			for (FilmShow filmShow : this.futureFilmShows) {
				if (this.selectedFilm == -1 || (filmShow.getFilm().getId() == this.selectedFilm)) {
					availableCinemas.put(filmShow.getCinema().getId(), filmShow.getCinema().toString());
					availableDates.add(filmShow.getBeginning());
				}
			}
		}
		if (availableCinemas.size() == 1) {
			for (Map.Entry<Integer, String> entry : availableCinemas.entrySet()) {
				this.setSelectedCinema(entry.getKey());
			}
		}
		
		updateAvailableDates();
		return availableCinemas;
	}

	public Map<Integer, String> getAvailableFilms() {
		if (this.selectedCinema != 0) { // a cinema is selected, filter all
										// availableFilms
			availableFilms.clear();
			availableDates.clear();

			for (FilmShow filmShow : this.futureFilmShows) {
				if (this.selectedCinema == -1 || (filmShow.getCinema().getId() == this.selectedCinema)) {
					availableFilms.put(filmShow.getFilm().getId(), filmShow.getFilm().toString());
					availableDates.add(filmShow.getBeginning());
				}
			}
		}
		if (availableFilms.size() == 1) {
			for (Map.Entry<Integer, String> entry : availableFilms.entrySet()) {
				this.setSelectedFilm(entry.getKey());
			}
		}

		updateAvailableDates();
		return availableFilms;
	}
	
	public String next(){
		if (this.selectedCinema<=0 ){
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select a cinema!",
							null));
			return null;
		}
		
		if (this.selectedFilm <=0){
			
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select a film!",
							null));
			
			return null;
		}
		
		if (this.selectedDate == null){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Select a date!",
							null));
			
			return null;
		}
		
		FilmShow selectedFilmShow = null;
		for (FilmShow filmShow: this.futureFilmShows){
			
			if (filmShow.getCinema().getId() == this.selectedCinema
				&& filmShow.getFilm().getId() == this.selectedFilm
				&& filmShow.getBeginning().equals(this.selectedDate)
					){
				selectedFilmShow = filmShow;
				break;
			}
			
		}
		
		if (selectedFilmShow == null){
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No film show found for the selection!",
							null));
			
			return null;
		}
		
		selectedFilmShow = this.filmService.loadComplete(selectedFilmShow);
		
		ExternalContext extCtx = FacesContext.getCurrentInstance()
				.getExternalContext();
		extCtx.getFlash().put(SELECTED_FILM_SHOW, selectedFilmShow);
		
		return "selectSeat?faces-redirect=true";
	}

}

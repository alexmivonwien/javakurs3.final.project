package at.alex.javakurs3.cinema.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.service.FilmService;

@ManagedBean
@ViewScoped
public class FilmAndCinemaChooserBean {
	
	@Inject
	private FilmService filmService;
	
	private List <FilmShow> futureFilmShows;
	private Cinema selectedCinema;
	private Film selectedFilm;
	
	private Map<Integer, Cinema> availableCinemas  = new HashMap<>();
	private Map <Integer, Film> availableFilms = new HashMap<>();
	
	
	@PostConstruct
	private void init(){
		futureFilmShows = filmService.findFilmShows(StringUtils.EMPTY);
		
		for (FilmShow filmShow : futureFilmShows){
			availableCinemas.put(filmShow.getCinema().getId(), filmShow.getCinema());
			availableFilms.put(filmShow.getFilm().getId(), filmShow.getFilm());
		}
	}


	public Cinema getSelectedCinema() {
		return selectedCinema;
	}


	public void setSelectedCinema(Cinema selectedCinema) {
		this.selectedCinema = selectedCinema;
	}


	public Film getSelectedFilm() {
		return selectedFilm;
	}


	public void setSelectedFilm(Film selectedFilm) {
		this.selectedFilm = selectedFilm;
	}


	public Map<Integer, Cinema> getAvailableCinemas() {
		return availableCinemas;
	}


	public void setAvailableCinemas(Map<Integer, Cinema> availableCinemas) {
		this.availableCinemas = availableCinemas;
	}


	public Map<Integer, Film> getAvailableFilms() {
		return availableFilms;
	}


	public void setAvailableFilms(Map<Integer, Film> availableFilms) {
		this.availableFilms = availableFilms;
	}
	
}

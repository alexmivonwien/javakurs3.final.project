package at.alex.javakurs3.cinema.model;

import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class FilmShow {
	
	@OneToOne
	private Film film;
	private LocalDateTime begining;
	private LocalDateTime end;
	
	@OneToOne
	private Cinema cinema;
	
	@OneToMany
	private Set <SeatForShow> seatsForShow;
	
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	public LocalDateTime getBegining() {
		return begining;
	}
	public void setBegining(LocalDateTime begining) {
		this.begining = begining;
	}
	public LocalDateTime getEnd() {
		return end;
	}
	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	public Cinema getCinema() {
		return cinema;
	}
	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}
	public Set<SeatForShow> getSeatsForShow() {
		return seatsForShow;
	}
	public void setSeatsForShow(Set<SeatForShow> seatsForShow) {
		this.seatsForShow = seatsForShow;
	}
	
}

package at.alex.javakurs3.cinema.model;

import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "filmshow")
@Access(AccessType.FIELD)
public class FilmShow {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@JoinColumn(name = "film_id", nullable=false)
	@ManyToOne( optional = false )
	private Film film;
	private Date begining;
	private Date end;
	
	@JoinColumn(name = "cinema_id", nullable=false)
	@ManyToOne( optional = false )
	private Cinema cinema;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SeatForShow.class, mappedBy = "filmShow")
	private Set<SeatForShow> seatsForShow;
	
	public FilmShow(Cinema cinema, Film film, Date begining, Date end) {
		this.cinema = cinema;
		this.film = film;
		this.begining = begining;
		this.end = end;
		seatsForShow = new LinkedHashSet<SeatForShow>();

		for (Seat seat : cinema.getAllSeats()) {
			SeatForShow seatForShow = new SeatForShow(seat, this);
			seatsForShow.add(seatForShow);
			seatForShow.setFilmShow(this);
		}
	}

	@Override
	public String toString() {
		return "FilmShow: cinema = " + cinema.getName() + "; film = " + film.getName() + "; beginning = " + this.begining;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Film getFilm() {
		return film;
	}
	public void setFilm(Film film) {
		this.film = film;
	}
	
	public Date getBegining() {
		return begining;
	}
	public void setBegining(Date begining) {
		this.begining = begining;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
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

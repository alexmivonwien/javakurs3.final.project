package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

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
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@JoinColumn(name = "film_id", nullable = false)
	@ManyToOne(optional = false)
	private Film film;
	private Date beginning;
	private Date end;

	@JoinColumn(name = "cinema_id", nullable = false)
	@ManyToOne(optional = false)
	private Cinema cinema;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = SeatForShow.class, mappedBy = "filmShow")
	private Set<SeatForShow> seatsForShow;

	public FilmShow() {
		this.seatsForShow = new TreeSet <>();

	}

	public FilmShow(Cinema cinema, Film film, Date beginning, Date end, BigDecimal minimumPrice) {
		this.cinema = cinema;
		this.film = film;
		this.beginning = beginning;
		this.end = end;
		seatsForShow = new TreeSet<SeatForShow>( new Comparator<SeatForShow>() {
			@Override
			public int compare(SeatForShow o1, SeatForShow o2) {
				if (o1.getRowNo() == o2.getRowNo()){
					return o1.getSeatNo() - o2.getSeatNo();
				}
				return o1.getRowNo() - o2.getRowNo();
			}

		});

		for (Seat seat : cinema.getAllSeats()) {
			SeatForShow seatForShow = new SeatForShow(seat, this);
			seatForShow.setPrice(minimumPrice);
			seatsForShow.add(seatForShow);
		}
	}

	@Override
	public String toString() {
		return "FilmShow: cinema = " + cinema.getName() + "; film = " + film.getName() + "; beginning = "
				+ this.beginning;
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

	public Date getBeginning() {
		return beginning;
	}

	public void setBeginning(Date beginning) {
		this.beginning = beginning;
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
		this.seatsForShow.addAll(seatsForShow);
	}
	
	/**
	 * This method assumes for simplicity that each row has the same number of seats (columns)
	 * In the real life, columns (or seats) per row may differ.
	 * 
	 * @return
	 */
	public int getNumberOfColumns(){
		int noColumns = 0;
		
		for (SeatForShow seatForShow : this.getSeatsForShow()){
			if (seatForShow.getRowNo() == (1)){
				noColumns ++;
			} 
		}
		
		return noColumns;
	}

	@Override
	public boolean equals(Object another) {

		if (another == null || !(another instanceof FilmShow))
			return false;
		
		FilmShow anotherFilmShow = (FilmShow)another;
		
		return this.cinema.equals(anotherFilmShow.cinema)
				&& this.film.equals(anotherFilmShow.film)
				&& this.beginning.equals(anotherFilmShow.beginning);

	}

	@Override
	public int hashCode() {
		return this.cinema.hashCode() ^ this.film.hashCode() ^ this.beginning.hashCode();
	}
}

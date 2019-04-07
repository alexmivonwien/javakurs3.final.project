package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;
import java.util.Set;

public class Reservation {

	private Customer customer;
	private FilmShow filmShow;
	private Set<Seat> seatsReserved;
	private BigDecimal totalPrice;
	
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public FilmShow getFilmShow() {
		return filmShow;
	}
	public void setFilmShow(FilmShow filmShow) {
		this.filmShow = filmShow;
	}
	public Set<Seat> getSeatsReserved() {
		return seatsReserved;
	}
	public void setSeatsReserved(Set<Seat> seatsReserved) {
		this.seatsReserved = seatsReserved;
	}
}

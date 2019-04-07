package at.alex.javakurs3.cinema.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Customer;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Reservation;
import at.alex.javakurs3.cinema.model.Seat;
import at.alex.javakurs3.cinema.model.SeatForShow;

@Stateless(mappedName = "ReservationService")
public class ReservationService {
	
	@Inject
	//private FilmService filmService;

	@PersistenceContext
	private EntityManager em;

	
	public Reservation createReservation (Customer customer, FilmShow filmShow, Set<Seat> seats){
		
		BigDecimal totalPrice = BigDecimal.ZERO;
		
		// 1.) Mark the seats as occupied and compute the total price:
		for (SeatForShow seatForShow: filmShow.getSeatsForShow()){
			seatForShow.setFree(false);
			totalPrice.add(seatForShow.getPrice());
		}
		// 2.) update the FilmShow entry:
		this.em.merge(filmShow);
		
		// 3.) Create a reservation and save it:
		Reservation reservation = new Reservation();
		reservation.setCustomer(customer);
		reservation.setFilmShow(filmShow);
		reservation.setSeatsReserved(seats);
		reservation.setTotalPrice(totalPrice);
		
		em.persist(reservation);
		
		return reservation;
	}

}

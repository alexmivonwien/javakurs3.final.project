package at.alex.javakurs3.cinema.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.alex.javakurs3.cinema.model.Customer;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Reservation;
import at.alex.javakurs3.cinema.model.Seat;
import at.alex.javakurs3.cinema.model.SeatForShow;

@Stateless(mappedName = "ReservationService")
public class ReservationService {


	@PersistenceContext
	private EntityManager em;

	public Reservation createReservation(Customer customer, FilmShow filmShow, Set<Seat> seats) {

		BigDecimal totalPrice = BigDecimal.ZERO;

		// 3.) Create a reservation and save it:
		Reservation reservation = new Reservation();
		Set<SeatForShow> seatsReserved = new HashSet<>();

		// 1.) Mark the seats as occupied and compute the total price:
		for (SeatForShow seatForShow : filmShow.getSeatsForShow()) {
			if (seats.contains(seatForShow)) {
				seatForShow.setFree(false);
				seatsReserved.add(seatForShow);
				totalPrice.add(seatForShow.getPrice());
			}
		}
		// 2.) update the FilmShow entry:
		this.em.merge(filmShow);

		reservation.setCustomer(customer);
		reservation.setFilmShow(filmShow);
		reservation.setSeatsReserved(seatsReserved);
		reservation.setTotalPrice(totalPrice);

		em.persist(reservation);

		return reservation;
	}

}

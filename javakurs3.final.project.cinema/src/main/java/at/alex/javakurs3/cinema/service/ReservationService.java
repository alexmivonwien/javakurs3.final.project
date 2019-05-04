package at.alex.javakurs3.cinema.service;

import java.math.BigDecimal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import at.alex.javakurs3.cinema.model.Customer;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Reservation;
import at.alex.javakurs3.cinema.model.Seat;
import at.alex.javakurs3.cinema.model.SeatForShow;
/**
 * 
 * @author User
 * @see https://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge
 * @see https://stackoverflow.com/questions/15198675/javax-persistence-persistenceexception-org-hibernate-persistentobjectexception
 * 
 * 
 */
@Stateless(mappedName = "ReservationService")
public class ReservationService {


	@PersistenceContext
	private EntityManager em;

	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveReservation(Reservation reservation) {
		
//		Set <SeatForShow> mergedSeatsForShowSet = new HashSet <>();
//		
//		for (SeatForShow seatForShow : reservation.getSeatsReserved()){
//			SeatForShow mergedSeatForShow = this.em.merge(seatForShow);
//			mergedSeatsForShowSet.add(mergedSeatForShow);
//		}
//		reservation.setSeatsReserved(mergedSeatsForShowSet);
//		this.em.merge(reservation.getFilmShow());
		this.em.merge(reservation);
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Reservation createReservation(Customer customer, FilmShow filmShow, Set<Seat> seats) {

		BigDecimal totalPrice = BigDecimal.ZERO;

		// 3.) Create a reservation and save it:
		Set<SeatForShow> seatsReserved = new HashSet<>();
		Reservation reservation = new Reservation(customer, filmShow, seatsReserved);
		

		// 1.) Mark the seats as occupied and compute the total price:
		for (SeatForShow seatForShow : filmShow.getSeatsForShow()) {
			if (seats.contains(seatForShow)) {
				seatForShow.setReservation(reservation);
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

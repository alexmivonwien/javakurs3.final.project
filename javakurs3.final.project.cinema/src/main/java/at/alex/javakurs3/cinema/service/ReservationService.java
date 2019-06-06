package at.alex.javakurs3.cinema.service;

import java.math.BigDecimal;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.CascadeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.hibernate.LockMode;

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

	
	/**
	 * this method demonstrates:
	 * 1.) Setting a pessimistic lock on each seatForShow entity - a lock is needed here,
	 * because otherwise a concurrent transaction may create another (different) reservation
	 * for exactly the same filmShow and (one or more of ) the same SeatForShow-s
	 *  
	 *  2.) the difference between em.persist(reservation) and em.merge(reservation)
	 * ( Reservation has CascadeType.ALL @OneToMany relationship with SeatForShow), so the merge operation is cascaded.
	 *  
	 * Even if a em.persist(reservation) is called with a new reservation just created, the operation fails,
	 * because the associated  SeatForShow objects are not new, they are already existing in the database.
	 * On the other hand, em.merge(reservation) succeeds, because it merges the existing SeatForShow in the database
	 * 
	 * @param reservation
	 * 
	 * @see https://stackoverflow.com/questions/1069992/jpa-entitymanager-why-use-persist-over-merge
	 * 
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveReservation(Reservation reservation) {

		for (SeatForShow seatForShow : reservation.getSeatsReserved()){
			 seatForShow = this.em.merge(seatForShow); // a merge is needed here, because seatForShow entities are detached at this moment 
			 this.em.lock(seatForShow, LockModeType.PESSIMISTIC_WRITE);
		}
		this.em.merge(reservation);
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Reservation createReservation(Customer customer, FilmShow filmShow, Set<Seat> seats) {

		BigDecimal totalPrice = BigDecimal.ZERO;

		// 3.) Create a reservation and save it:
		Set<SeatForShow> seatsReserved = new HashSet<>();
		Reservation reservation = new Reservation(customer, filmShow, seatsReserved);
		

		// 1.) Mark the seats as occupied and compute the total price:
		for (SeatForShow seatForShow : filmShow.getSeatsForShow()) {
			if (seats.contains(seatForShow)) {
				seatForShow = this.em.merge(seatForShow); // a merge is needed here, because seatForShow entities are detached at this moment 
				 this.em.lock(seatForShow, LockModeType.PESSIMISTIC_WRITE);
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

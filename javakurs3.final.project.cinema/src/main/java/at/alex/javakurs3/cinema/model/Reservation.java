package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
@Table(name = "reservation")
@Access(AccessType.FIELD)
public class Reservation {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@JoinColumn(name = "customer_id", nullable=false)
	@ManyToOne( optional = false )
	private Customer customer;
	
	@JoinColumn(name = "filmshow_id", nullable=false)
	@ManyToOne( optional = false )
	private FilmShow filmShow;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = SeatForShow.class, mappedBy = "reservation")
	private Set<SeatForShow> seatsReserved;
	
	private BigDecimal totalPrice =  BigDecimal.ZERO;
	
	
	public Reservation(Customer customer, FilmShow filmShow, Set<SeatForShow> seatsReserved){
		this.customer = customer;
		this.filmShow = filmShow;
		this.seatsReserved = seatsReserved;
		
		for (SeatForShow seatForShow : seatsReserved){
			totalPrice.add(seatForShow.getPrice());
		}
		totalPrice.setScale(2,  RoundingMode.CEILING);
	}
	
	
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

	public Set<SeatForShow> getSeatsReserved() {
		return seatsReserved;
	}

	public void setSeatsReserved(Set<SeatForShow> seatsReserved) {
		this.seatsReserved = seatsReserved;
	}
}

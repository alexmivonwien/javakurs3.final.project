package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "seatforshow")
@Access(AccessType.FIELD)
public class SeatForShow implements Comparable <SeatForShow>, Seatable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private byte rowNo;
	private byte seatNo;
	
	@Transient
	private String color;

	private BigDecimal price;
	
	@ManyToOne( optional = false )
	private FilmShow filmShow;
	
	@JoinColumn(name = "reservation_id", nullable=true)
	@ManyToOne( optional = true )
	private Reservation reservation;
	
	public SeatForShow(){
		
	}
	
	public SeatForShow(Seat seat, FilmShow filmShow) {
		this.rowNo = seat.getRowNo();
		this.seatNo = seat.getSeatNo();
		this.filmShow = filmShow;

		if (!filmShow.getCinema().getAllSeats().contains(seat)) {
			throw new IllegalArgumentException(" The seat " + seat.toString() + " does not belong to the filmShow " + filmShow);
		}
	}
	
	public String getColor() {
		return this.getReservation() == null ? "green" : "red";
	}

	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public byte getRowNo() {
		return rowNo;
	}
	public void setRowNo(byte rowNo) {
		this.rowNo = rowNo;
	}
	public byte getSeatNo() {
		return seatNo;
	}
	public void setSeatNo(byte seatNo) {
		this.seatNo = seatNo;
	}

	
	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public FilmShow getFilmShow() {
		return filmShow;
	}
	public void setFilmShow(FilmShow filmShow) {
		this.filmShow = filmShow;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof SeatForShow) || !(another instanceof Seat))
			return false;

		SeatForShow anotherSeatForShow = another instanceof SeatForShow ? (SeatForShow) another : null;
		Seat anotherSeat = another instanceof Seat ? (Seat) another : null;

		if (anotherSeatForShow != null) {
			return this.rowNo == anotherSeatForShow.rowNo && this.seatNo == anotherSeatForShow.seatNo && this.filmShow.equals(anotherSeatForShow.getFilmShow());
		}

		return this.rowNo == anotherSeat.getRowNo() && this.seatNo == anotherSeat.getSeatNo() && this.filmShow.getCinema().equals(anotherSeat.getCinema());
	}

	@Override
	public int hashCode() {
		return this.rowNo ^ this.seatNo ^ this.filmShow.hashCode();
	}

	@Override
	public int compareTo(SeatForShow seatForShow) {
		int diff = this.rowNo - seatForShow.getRowNo();

		if (diff != 0) {
			return diff;
		}
		return this.seatNo - seatForShow.getSeatNo();
	}
	
	@Override
	public String toString(){
		return "Row No: " + this.rowNo + ", Seat No: " + this.seatNo + ", filmShowId= " + this.filmShow.getId();
	}
	
	public String getHeader(){
		return this.rowNo + "/" + this.seatNo;
	}
}

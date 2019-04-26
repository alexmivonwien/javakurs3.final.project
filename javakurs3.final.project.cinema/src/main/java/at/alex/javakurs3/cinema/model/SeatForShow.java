package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "seatforshow")
@Access(AccessType.FIELD)
public class SeatForShow implements Seatable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private byte rowNo;
	private byte seatNo;

	private boolean isFree = true;
	private BigDecimal price;
	
	//@JoinColumn(name = "filmshow_id", nullable=false)
	@ManyToOne( optional = false )
	private FilmShow filmShow;
	
	public SeatForShow(Seat seat, FilmShow filmShow) {
		this.rowNo = seat.getRowNo();
		this.seatNo = seat.getSeatNo();
		this.filmShow = filmShow;

		if (!filmShow.getCinema().getAllSeats().contains(seat)) {
			throw new IllegalArgumentException(" The seat " + seat.toString() + " does not belong to the filmShow " + filmShow);
		}
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
	public boolean isFree() {
		return isFree;
	}
	public void setFree(boolean isFree) {
		this.isFree = isFree;
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
	public int compareTo(Seatable seat) {
		int diff = this.rowNo - seat.getRowNo();

		if (diff != 0) {
			return diff;
		}
		return this.seatNo - seat.getSeatNo();
	}
	
}

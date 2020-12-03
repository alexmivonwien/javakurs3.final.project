package at.alex.javakurs3.cinema.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "seat")
@Access(AccessType.FIELD)
public class Seat implements Comparable <Seat>, Seatable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private byte rowNo;
	private byte seatNo;
	
	@ManyToOne( optional = false )
	private Cinema cinema;
	
	@Override
	public boolean equals (Object another){
		if (another == null || !(another instanceof Seat) || !(another instanceof SeatForShow))
			return false;
		
		Seat anotherSeat = another instanceof Seat ? (Seat) another : null;
		SeatForShow anotherSeatForShow = another instanceof SeatForShow ? (SeatForShow) another : null;
		
		if (anotherSeat != null) {
			return this.rowNo == anotherSeat.rowNo && this.seatNo == anotherSeat.seatNo && this.cinema.equals(anotherSeat.getCinema());
		}

		return this.rowNo == anotherSeatForShow.getRowNo() && this.seatNo == anotherSeatForShow.getSeatNo() && this.cinema.equals(anotherSeatForShow.getFilmShow().getCinema());
	}
	
	
	@Override
	public int hashCode (){
		return this.rowNo ^ this.seatNo ^ this.cinema.hashCode();
	}
	
	@Override
	public String toString() {
		return "Seat row = " + this.getRowNo() + "; seatNo = " + this.seatNo + "; cinema = " + cinema.getName();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Cinema getCinema() {
		return cinema;
	}

	public void setCinema(Cinema cinema) {
		this.cinema = cinema;
	}

	@Override
	public int compareTo(Seat seat) {
		int diff = this.rowNo - seat.getRowNo();
		
		if (diff != 0){
			return diff;
		}
		return this.seatNo - seat.getSeatNo();
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
}

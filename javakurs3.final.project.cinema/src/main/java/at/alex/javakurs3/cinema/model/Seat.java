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
public class Seat implements Comparable <Seat>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	protected byte rowNo;
	protected byte seatNo;
	
	@ManyToOne( optional = false )
	private Cinema cinema;
	
	
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
	public int compareTo (Seat  seat){
		int diff = this.rowNo - seat.rowNo;
		
		if (diff != 0){
			return diff;
		}
		return this.seatNo - seat.seatNo;
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

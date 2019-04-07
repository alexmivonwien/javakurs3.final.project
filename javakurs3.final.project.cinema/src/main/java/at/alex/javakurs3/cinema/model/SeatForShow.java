package at.alex.javakurs3.cinema.model;

import java.math.BigDecimal;

public class SeatForShow extends Seat {

	private boolean isFree = true;
	private BigDecimal price;
	private FilmShow filmShow;
	
	
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
	
}

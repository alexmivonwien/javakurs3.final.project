package at.alex.javakurs3.cinema.model;

public interface Seatable extends Comparable<Seatable> {

	public byte getRowNo();

	public void setRowNo(byte rowNo);

	public byte getSeatNo();

	public void setSeatNo(byte seatNo);
}

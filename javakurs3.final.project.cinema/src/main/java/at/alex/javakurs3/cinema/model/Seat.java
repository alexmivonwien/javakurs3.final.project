package at.alex.javakurs3.cinema.model;

public class Seat implements Comparable <Seat>{

	protected byte rowNo;
	protected byte seatNo;
	
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

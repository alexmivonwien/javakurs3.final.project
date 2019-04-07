package at.alex.javakurs3.cinema.model;

import java.util.Set;

public class Cinema {
	
	private String name;
	private Address address;
	private Set<Seat> allSeats;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Set<Seat> getAllSeats() {
		return allSeats;
	}
	public void setAllSeats(Set<Seat> allSeats) {
		this.allSeats = allSeats;
	}
	
}

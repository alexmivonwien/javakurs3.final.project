package at.alex.javakurs3.cinema.model;

import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cinema")
@Access(AccessType.FIELD)
public class Cinema {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private String name;
	
	@OneToOne
	private Address address;
	
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Seat.class, mappedBy = "cinema")
	private Set<Seat> allSeats;
	
	public Cinema(){
		
	}
	
	public Cinema(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public Address getAddress() {
//		return address;
//	}
//	public void setAddress(Address address) {
//		this.address = address;
//	}
	public Set<Seat> getAllSeats() {
		return allSeats;
	}
	public void setAllSeats(Set<Seat> allSeats) {
		this.allSeats = allSeats;
	}
	
	@Override
	public boolean equals(Object another) {
		if (another == null || !(another instanceof Cinema)) {
			return false;
		}

		Cinema anotherCinema = (Cinema) another;
		return this.name.equals(anotherCinema.getName());

	}

	@Override
	public int hashCode() {
		return this.name.hashCode();
	}

	@Override
	public String toString(){
		return this.name;
	}
}

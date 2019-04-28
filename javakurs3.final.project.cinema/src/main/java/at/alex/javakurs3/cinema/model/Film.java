package at.alex.javakurs3.cinema.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

@Entity
@Table(name = "film")
@Access(AccessType.FIELD)
public class Film {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String name;
	private byte lengthMin;
	private short year;
	private String director;
	
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
	public byte getLengthMin() {
		return lengthMin;
	}
	public void setLengthMin(byte lengthMin) {
		this.lengthMin = lengthMin;
	}
	public short getYear() {
		return year;
	}
	public void setYear(short year) {
		this.year = year;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	
	@Override
	public boolean equals(Object another) {

		if (another == null || !(another instanceof Film))
			return false;
		
		Film anotherFilm = (Film)another;
		
		return this.name.equals(anotherFilm.name)
				&& this.director.equals(anotherFilm.director)
				&& this.year == anotherFilm.getYear()
				&& this.lengthMin == anotherFilm.lengthMin;

	}

	@Override
	public int hashCode() {
		return this.name.hashCode() ^ this.director.hashCode() ^ this.year ^ this.lengthMin;
	}
	
	
	@Override
	public String toString(){
		return this.name + ", " + this.year + (this.getDirector() !=null ? ", " + this.getDirector() : StringUtils.EMPTY);
	}
	
	
	
}

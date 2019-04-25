package at.alex.javakurs3.cinema.hibernate.standalone;

import java.math.BigDecimal;
import java.sql.Date;
//import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Seat;
import at.alex.javakurs3.cinema.model.SeatForShow;


public class Action {
	
	
	static Cinema createCinemaWithSeats(String cinemaName){
		
    	Cinema cinema = new Cinema();
    	cinema.setName(cinemaName);
    	
    	Set <Seat> seatSet = new HashSet<>();

    	Seat seat1 = new Seat();
    	seat1.setRowNo((byte)1);
    	seat1.setSeatNo((byte)1);
    	seat1.setCinema(cinema);
    	seatSet.add(seat1);
    	
     	Seat seat2 = new Seat();
    	seat2.setRowNo((byte)1);
    	seat2.setSeatNo((byte)2);
    	seat2.setCinema(cinema);
    	seatSet.add(seat2);
    	
    	if (cinemaName.toLowerCase().contains("donaustadt")){
	     	Seat seat3 = new Seat();
	    	seat3.setRowNo((byte)1);
	    	seat3.setSeatNo((byte)3);
	    	seat3.setCinema(cinema);
	    	seatSet.add(seat3);
    	}
        
    	cinema.setAllSeats(seatSet);
    	
    	return cinema;
	}
	
	static Film createFilm(String name, short year, byte lengthMin){
    	Film film2 = new Film();
    	film2.setName(name);
    	film2.setLengthMin(lengthMin);
    	film2.setYear(year);
		
		return film2;
	}
	
	static Date calculateEndDate (Date beginning, byte lengthMin){

		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(beginning);
		cal.add(Calendar.MINUTE, lengthMin);
		
		return new Date (cal.getTime().getTime());
		
	}
	
	static FilmShow createFilmShow(Session session, String filmeName, String cinemaName, Date beginning){

		FilmShow show = new FilmShow();
    	
    	org.hibernate.Query query = session.createQuery("from Film f where f.name = :nn");
    	query.setParameter("nn", filmeName);
    	List<Film> films = query.list();
    	Film film = films.get(0);
    	show.setFilm(film);
    	show.setBegining(beginning);    	
    	show.setEnd(calculateEndDate(beginning, film.getLengthMin()));
       	
    	org.hibernate.Query query2 = session.createQuery("from Cinema  c where c.name = :nn");
    	query2.setParameter("nn", cinemaName);
    	List<Cinema> cinemas = query2.list();
    	Cinema cinema = cinemas.get(0); 
    	show.setCinema(cinema);
		
    	Set <SeatForShow> seatForShowSet = new LinkedHashSet<>();
    	
    	for (Seat seat: cinema.getAllSeats()){
    		SeatForShow seatForShow = new SeatForShow();
    		seatForShow.setCinema(cinema);
    		seatForShow.setFilmShow(show);
    		seatForShow.setRowNo(seat.getRowNo());
    		seatForShow.setSeatNo(seat.getSeatNo());
    		seatForShow.setFree(true);
    		seatForShow.setPrice( new BigDecimal("20").multiply(new BigDecimal(Math.random())));
    		seatForShowSet.add(seatForShow);
    	}
    	
    	show.setSeatsForShow(seatForShowSet);
		
    	return show;
	}
	
	
    public static void main(String args[]){
    	Configuration configuration = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	SessionFactory factory = configuration.buildSessionFactory(builder.build());
    	Session session = factory.openSession();
    	
    	session.beginTransaction();
    	Film film1 = createFilm ("Star Wars", (short)1978, (byte)120);
     	Film film2 = createFilm ("Empire strikes back", (short)1979, (byte)121);
     	
     	session.persist(film1);
     	session.persist(film2);

     	Cinema cinema1 = createCinemaWithSeats("Cinaplex Donaustadt");
     	Cinema cinema2 = createCinemaWithSeats("Cinaplex InnereStadt");
     	
     	session.persist(cinema1);
     	session.persist(cinema2);
     	
    	session.getTransaction().commit();
    	session.clear();
    	session.close();
     	
    	session = factory.openSession();
    	session.beginTransaction();

    	FilmShow show = createFilmShow(session, "Star wars", "Cinaplex Innenstadt", new Date( new java.util.Date().getTime()));
    	session.persist(show);
    	session.getTransaction().commit();
    }
}
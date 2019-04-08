package at.alex.javakurs3.cinema.hibernate.standalone;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;
import at.alex.javakurs3.cinema.model.Seat;


public class Action {
	
    public static void main(String args[]){
    	Configuration configuration = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	SessionFactory factory = configuration.buildSessionFactory(builder.build());
    	Session session = factory.openSession();
//    	Cinema cinema = new Cinema();
//    	cinema.setName("Cinaplex Innenstadt");
//    	
//    	Set <Seat> seatSet = new HashSet<>();
//    	
//    	Seat seat1 = new Seat();
//    	seat1.setRowNo((byte)1);
//    	seat1.setSeatNo((byte)1);
//    	seat1.setCinema(cinema);
//    	seatSet.add(seat1);
//    	
//     	Seat seat2 = new Seat();
//    	seat2.setRowNo((byte)1);
//    	seat2.setSeatNo((byte)2);
//    	seat2.setCinema(cinema);
//    	seatSet.add(seat2);
    	
//     	Seat seat3 = new Seat();
//    	seat3.setRowNo((byte)1);
//    	seat3.setSeatNo((byte)3);
//    	seat3.setCinema(cinema);
//    	seatSet.add(seat3);
        
 //   	cinema.setAllSeats(seatSet);
    	
    	FilmShow show = new FilmShow();
    	show.setBegining(LocalDateTime.now());
    	
    	org.hibernate.Query query = session.createQuery("from Film f where f.name = :nn");
    	query.setParameter("nn", "Star wars");
    	List<Film> films = query.list();
    	show.setFilm(films.get(0));
    	
    	
    	org.hibernate.Query query2 = session.createQuery("from Cinema  c where c.name = :nn");
    	query2.setParameter("nn", "Cinaplex Innenstadt");
    	List<Cinema> cinemas = query2.list();
    	
    	
    	show.setCinema(cinemas.get(0));
    	
//    	Film film1 = new Film();
//    	film1.setName("Star wars");
//    	film1.setLengthMin((byte)120);
//    	film1.setYear((short)1978);
    	
//    	Film film2 = new Film();
//    	film2.setName("Empire strikes back");
//    	film2.setLengthMin((byte)121);
//    	film2.setYear((short)1981);
    	
    	session.beginTransaction();
 //   	session.persist(cinema);
//    	session.persist(film1);
//    	session.persist(film2);
    	session.persist(show);
    	session.getTransaction().commit();
    }
}
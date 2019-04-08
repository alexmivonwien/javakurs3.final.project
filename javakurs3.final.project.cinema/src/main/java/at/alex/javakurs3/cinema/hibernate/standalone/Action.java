package at.alex.javakurs3.cinema.hibernate.standalone;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Seat;


public class Action {
	
    public static void main(String args[]){
    	Configuration configuration = new Configuration().configure();
    	StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
    	SessionFactory factory = configuration.buildSessionFactory(builder.build());
    	Session session = factory.openSession();
    	Cinema cinema = new Cinema();
    	cinema.setName("Cinaplex Donaustadt");
    	
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
    	
     	Seat seat3 = new Seat();
    	seat3.setRowNo((byte)1);
    	seat3.setSeatNo((byte)3);
    	seat3.setCinema(cinema);
    	seatSet.add(seat3);
        
    	cinema.setAllSeats(seatSet);
    	session.beginTransaction();
    	session.persist(cinema);
    	session.getTransaction().commit();
    }
}
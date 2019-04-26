package at.alex.javakurs3.cinema.service;

import java.sql.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import at.alex.javakurs3.cinema.hibernate.standalone.Action;
import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;

@Stateless(mappedName = "FilmService")
public class FilmService {

	@PersistenceContext
	private EntityManager em;

	// public List <FilmShow> findFilmShows (Film film, Cinema cinema,
	// LocalDateTime startDate){
	//
	// }

	public List<FilmShow> findFilmShows(String filmName) {

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<FilmShow> q = cb.createQuery(FilmShow.class);
		Root<FilmShow> root = q.from(FilmShow.class);
		q.select(root);
		q.where(cb.equal(root.get("film").get("name"), filmName));

		List<FilmShow> result = this.em.createQuery(q).getResultList();

		return result;
	}

	public void createEntitites() {

		Film film1 = Action.createFilm("Star Wars", (short) 1978, (byte) 120);
		Film film2 = Action.createFilm("Empire strikes back", (short) 1979, (byte) 121);

		em.persist(film1);
		em.persist(film2);

		Cinema cinema1 = Action.createCinemaWithSeats("Cinaplex Donaustadt");
		Cinema cinema2 = Action.createCinemaWithSeats("Cinaplex InnereStadt");

		em.persist(cinema1);
		em.persist(cinema2);



		FilmShow show = Action.createFilmShow(em, "Star wars", "Cinaplex Innenstadt", new Date(new java.util.Date().getTime()));
		em.persist(show);

	}

}

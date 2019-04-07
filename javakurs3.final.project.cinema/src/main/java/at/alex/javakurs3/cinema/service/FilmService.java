package at.alex.javakurs3.cinema.service;

import java.time.LocalDateTime;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import at.alex.javakurs3.cinema.model.Cinema;
import at.alex.javakurs3.cinema.model.Film;
import at.alex.javakurs3.cinema.model.FilmShow;

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

}

package at.alex.javakurs3.cinema.web.utils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import at.alex.javakurs3.cinema.model.Film;

/**
 * 
 * 
 * @author User
 * @see https://www.mkyong.com/jsf2/custom-converter-in-jsf-2-0/
 * 
 */
//@FacesConverter("FilmConvertrer")
public class FilmConvertrer implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		
		Film film = (Film)value;
		
		// TODO Auto-generated method stub
		return film.toString();
	}

}

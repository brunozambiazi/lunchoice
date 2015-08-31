
package com.brunozambiazi.lunchoice.frontend.controller;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.service.PersonService;
import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HomeController {
	
	@Autowired
	private PersonService personService;

	@Autowired
	private RestaurantService restaurantService;
	
	
	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("home");
	}
	
	@RequestMapping(value = "/vote", method = RequestMethod.POST)
	public ModelAndView vote(@RequestParam final String person, @RequestParam final String restaurant) {
		personService.vote(personService.findById(person), restaurantService.findById(restaurant));
		return new ModelAndView("redirect:/");
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(final Exception exception) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", exception);
		mv.setViewName("exception");
	    return mv;
	}
	

	@ModelAttribute("people")
	public Collection<Person> getPeople() {
		return personService.findAll();
	}
	
	@ModelAttribute("restaurants")
	public Collection<Restaurant> getRestaurants() {
		return restaurantService.findAll();
	}
	
}

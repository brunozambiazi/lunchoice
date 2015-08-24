
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.service.PersonService;
import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import com.brunozambiazi.lunchoice.backend.service.VoteService;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.framework.spring.Message;
import java.util.Calendar;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/applicationContext-test.xml"})
public abstract class BasicServiceTest {
	
	@Autowired
	protected Message message;
	
	@Autowired
	protected PersonService personService;

	@Autowired
	protected RestaurantService restaurantService;
	
	@Autowired
	protected VoteService voteService;

	
	@Before
	public void init() {
		for (Vote vote : voteService.findAll()) {
			voteService.remove(vote);
		}
		
		for (Restaurant restaurant : restaurantService.findAll()) {
			restaurantService.remove(restaurant);
		}
		
		for (Person person : personService.findAll()) {
			personService.remove(person);
		}
	}
	
	protected Person createPerson(String name) {
		Person person = new Person();
		person.setEmail(name+"@gmail.com");
		person.setName(name);
		return personService.save(person);
	}
	
	protected Restaurant createRestaurant(String name) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		return restaurantService.save(restaurant);
	}
	
	protected Vote createVote(Person person, Restaurant restaurant, Calendar date) {
		Vote vote = new Vote();
		vote.setId(new PersonalDailyVote());
		vote.getId().setDateCalendar(date);
		vote.getId().setPerson(person);
		vote.setRestaurant(restaurant);
		return voteService.save(vote);
	}
	
}

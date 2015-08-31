
package com.brunozambiazi.lunchoice.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.config.ApplicationConfig;
import com.brunozambiazi.lunchoice.config.AspectLimitVotingHourTest;
import com.brunozambiazi.lunchoice.config.AspectTest;
import com.brunozambiazi.lunchoice.config.EntityManagerFactoryTest;
import java.util.Calendar;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, 
	classes = {ApplicationConfig.class, AspectTest.class, EntityManagerFactoryTest.class})
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
	public void mockLimitHorForVoting() {
		AspectLimitVotingHourTest.LIMIT = Calendar.getInstance();
		AspectLimitVotingHourTest.LIMIT.add(Calendar.MINUTE, 5);
	}
	
	@Before
	public void truncateCollections() {
		for (Restaurant restaurant : restaurantService.findAll()) {
			restaurantService.remove(restaurant);
		}
		
		for (Person person : personService.findAll()) {
			personService.remove(person);
		}
		
		assertThat(personService.findAll()).isEmpty();
		assertThat(restaurantService.findAll()).isEmpty();
		assertThat(voteService.findAll()).isEmpty();
	}
	
	protected Person createPerson(String name) {
		Person person = new Person();
		person.setEmail(name+"@mailinator.com");
		person.setName(name);
		
		person = personService.save(person);
		assertThat(person.getId()).isNotNull();
		return person;
	}
	
	protected Restaurant createRestaurant(String name) {
		Restaurant restaurant = new Restaurant();
		restaurant.setName(name);
		
		restaurant = restaurantService.save(restaurant);
		assertThat(restaurant.getId()).isNotNull();
		return restaurant;
	}
	
	protected Vote createVote(Person person, Restaurant restaurant, Calendar date) {
		Vote vote = new Vote();
		vote.setId(new PersonalDailyVote());
		vote.getId().setDateCalendar(date);
		vote.getId().setPerson(person);
		vote.setRestaurant(restaurant);
		
		vote = voteService.save(vote);
		assertThat(vote.getId()).isNotNull();
		return vote;
	}
	
}

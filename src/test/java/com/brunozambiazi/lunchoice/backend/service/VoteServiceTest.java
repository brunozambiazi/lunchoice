
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.framework.util.CalendarUtil;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;


public class VoteServiceTest extends BasicServiceTest {

	@Test
	public void findByPersonOn() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		
		Restaurant restaurant = createRestaurant("restaurant");
		createVote(person1, restaurant, Calendar.getInstance());
		
		Assert.assertNotNull(voteService.findByPersonOn(person1, Calendar.getInstance()));
		Assert.assertNull(voteService.findByPersonOn(person1, CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT)));
		Assert.assertNull(voteService.findByPersonOn(person2, Calendar.getInstance()));
	}
	
	@Test
	public void findToday() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		
		Restaurant restaurant = createRestaurant("restaurant");

		Assert.assertEquals(0, voteService.findToday().size());
		
		createVote(person1, restaurant, Calendar.getInstance());
		Assert.assertEquals(1, voteService.findToday().size());
		
		createVote(person2, restaurant, Calendar.getInstance());
		Assert.assertEquals(2, voteService.findToday().size());
	}

	@Test
	public void mostVotedToday() {
		Assert.assertNull(voteService.mostVotedToday());
		
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Person person3 = createPerson("person3");
		Person person4 = createPerson("person4");
		Person person5 = createPerson("person5");
		
		Restaurant restaurant1 = createRestaurant("restaurant 1");
		Restaurant restaurant2 = createRestaurant("restaurant 2");
		
		createVote(person1, restaurant1, Calendar.getInstance());
		Assert.assertEquals(restaurant1, voteService.mostVotedToday());
		
		createVote(person2, restaurant2, Calendar.getInstance());
		Restaurant voted = voteService.mostVotedToday();
		Assert.assertTrue(voted.equals(restaurant1) || voted.equals(restaurant2));
		
		createVote(person3, restaurant2, Calendar.getInstance());
		Assert.assertEquals(restaurant2, voteService.mostVotedToday());
		
		createVote(person4, restaurant1, CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT));
		Assert.assertEquals(restaurant2, voteService.mostVotedToday());

		createVote(person5, restaurant2, Calendar.getInstance());
		Assert.assertEquals(restaurant2, voteService.mostVotedToday());
	}
	
}

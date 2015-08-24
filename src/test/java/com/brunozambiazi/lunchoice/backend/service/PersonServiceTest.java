
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.util.CalendarUtil;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;


public class PersonServiceTest extends BasicServiceTest {

	@Test(expected = NullPointerException.class)
	public void canVoteTodayNull() {
		personService.canVoteToday(null);
	}
	
	@Test
	public void canVoteToday() {
		Person person1 = createPerson("pessoa1");
		Person person2 = createPerson("pessoa2");

		Restaurant restaurant1 = createRestaurant("primeiro restaurante");

		Assert.assertTrue(personService.canVoteToday(person1));
		Assert.assertTrue(personService.canVoteToday(person2));
		
		createVote(person1, restaurant1, CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT));
		Assert.assertTrue(personService.canVoteToday(person1));
		Assert.assertTrue(personService.canVoteToday(person2));
		
		createVote(person1, restaurant1, Calendar.getInstance());
		Assert.assertFalse(personService.canVoteToday(person1));
		Assert.assertTrue(personService.canVoteToday(person2));
	}
	
	@Test
	public void vote() {
		Person person1 = createPerson("pessoa1");
		Person person2 = createPerson("pessoa2");

		Restaurant restaurant1 = createRestaurant("primeiro restaurante");
		Restaurant restaurant2 = createRestaurant("segundo restaurante");
		
		personService.vote(person1, restaurant1);
		personService.vote(person2, restaurant2);
		
		try {
			personService.vote(person1, restaurant1);
		} catch (BusinessException be) {
			Assert.assertEquals(message.get("person.already_voted_today"), be.getMessage());
		}
		
		try {
			personService.vote(person1, restaurant2);
		} catch (BusinessException be) {
			Assert.assertEquals(message.get("person.already_voted_today"), be.getMessage());
		}
	}
	
}

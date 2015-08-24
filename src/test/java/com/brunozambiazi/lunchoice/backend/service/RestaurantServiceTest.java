
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.lunchoice.backend.builder.VoteBuilder;
import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.util.CalendarUtil;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Test;


public class RestaurantServiceTest extends BasicServiceTest {

	@Test
	public void findAnyone() {
		Assert.assertNull(restaurantService.findAnyone());
		
		Restaurant restaurant1 = createRestaurant("restaurant 1");
		Assert.assertEquals(restaurant1, restaurantService.findAnyone());
		
		createRestaurant("restaurant 2");
		Assert.assertNotNull(restaurantService.findAnyone());
	}
	
	@Test
	public void makeChoiceToday() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Person person3 = createPerson("person3");
		
		Restaurant restaurant1 = createRestaurant("restaurant 1");
		Assert.assertEquals(restaurant1, restaurantService.makeChoiceToday());
		Assert.assertEquals(1, restaurantService.findById(restaurant1.getId()).getChosenWeeks().size());
		
		Restaurant restaurant2 = createRestaurant("restaurant 2");
		
		createVote(person1, restaurant1, Calendar.getInstance());
		Assert.assertEquals(restaurant1, restaurantService.makeChoiceToday());
		Assert.assertEquals(1, restaurantService.findById(restaurant1.getId()).getChosenWeeks().size());
		
		createVote(person2, restaurant2, CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT));
		createVote(person3, restaurant2, CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT));
		Assert.assertEquals(restaurant1, restaurantService.makeChoiceToday());
		Assert.assertEquals(1, restaurantService.findById(restaurant1.getId()).getChosenWeeks().size());
		
		createVote(person2, restaurant2, Calendar.getInstance());
		createVote(person3, restaurant2, Calendar.getInstance());
		Assert.assertEquals(restaurant2, restaurantService.makeChoiceToday());
		Assert.assertEquals(1, restaurantService.findById(restaurant2.getId()).getChosenWeeks().size());
	}
	
	@Test
	public void receive() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		
		Restaurant restaurant1 = createRestaurant("restaurant 1");
		Restaurant restaurant2 = createRestaurant("restaurant 2");
		
		restaurantService.receive(new VoteBuilder().from(person1).votingIn(restaurant1).on(Calendar.getInstance()).vote());
		restaurant1 = restaurantService.findById(restaurant1.getId());
		Assert.assertEquals(1, restaurant1.getVotes().size());
		
		restaurantService.receive(new VoteBuilder().from(person1).votingIn(restaurant2).on(CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT)).vote());
		restaurant2 = restaurantService.findById(restaurant2.getId());
		Assert.assertEquals(1, restaurant2.getVotes().size());
		
		try {
			restaurantService.receive(new VoteBuilder().from(person2).votingIn(restaurant1).on(Calendar.getInstance()).vote());
		} catch (BusinessException be) {
			Assert.assertEquals(message.get("restaurant.already_chosen_this_week"), be.getMessage());
		}
		
		restaurantService.receive(new VoteBuilder().from(person2).votingIn(restaurant1).on(CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT)).vote());
		restaurant1 = restaurantService.findById(restaurant1.getId());
		Assert.assertEquals(3, restaurant1.getVotes().size());
	}
	
}

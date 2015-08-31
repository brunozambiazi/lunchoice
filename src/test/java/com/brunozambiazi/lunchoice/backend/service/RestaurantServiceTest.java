
package com.brunozambiazi.lunchoice.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.util.CalendarUtil;
import com.brunozambiazi.lunchoice.backend.builder.VoteBuilder;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import java.util.Calendar;
import java.util.UUID;
import org.junit.Test;
import org.springframework.test.annotation.Repeat;


public class RestaurantServiceTest extends BasicServiceTest {
	
	@Test
	public void findAll() {
		int total = 25;
		
		for (int index = 0; index < total; index++) {
			createRestaurant(UUID.randomUUID().toString());
		}
		
		assertThat(restaurantService.findAll()).hasSize(total);
	}

	@Test
	public void findAnyone() {
		assertThat(restaurantService.findAnyone()).isNull();
		
		createRestaurant("restaurant1");
		assertThat(restaurantService.findAnyone()).isNotNull();
		
		createRestaurant("restaurant2");
		assertThat(restaurantService.findAnyone()).isNotNull();
	}
	
	@Test
	public void makeChoiceTodayWithoutRestaurants() {
		try {
			restaurantService.makeChoiceToday();
		} catch (BusinessException be) {
			assertThat(be.getMessage()).isEqualTo(message.get("restaurant.there_are_no_restaurants"));
		}
	}
	
	@Test
	public void makeChoiceTodayWithoutVotesAndJustOneRestaurant() {
		Restaurant restaurant = createRestaurant("restaurant");
		
		Restaurant chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).as("there is just one restaurant")
			.isNotNull()
			.isEqualTo(restaurant);
		assertThat(chosen.getChosenWeeks()).as("must have just one week chosen")
			.hasSize(1)
			.containsOnly(CalendarUtil.toString(Calendar.getInstance(), CalendarUtil.WEEK_FORMAT));
		
		chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).isNotNull();
		assertThat(chosen.getChosenWeeks()).as("must have just one week chosen yet")
			.hasSize(1);
	}
	
	@Test @Repeat(3)
	public void makeChoiceTodayWithoutVotesAndMoreThanOneRestaurant() {
		createRestaurant("restaurant1");
		createRestaurant("restaurant2");
		createRestaurant("restaurant3");
		assertThat(restaurantService.findAll()).hasSize(3);
		
		Restaurant chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).isNotNull();
		assertThat(chosen.getChosenWeeks()).hasSize(1);
	}
	
	@Test
	public void makeChoiceToday() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		Restaurant restaurant2 = createRestaurant("restaurant2");
		Restaurant restaurant3 = createRestaurant("restaurant3");
		
		createVote(person1, restaurant1, Calendar.getInstance());
		Restaurant chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).as("restaurant 1 was the only voted")
			.isNotNull()
			.isEqualTo(restaurant1);
		
		createVote(person2, restaurant2, Calendar.getInstance());
		chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).as("both restaurant 1 and restaurant 2 can be chosen")
			.isNotNull()
			.isIn(restaurant1, restaurant2);
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		createVote(person1, restaurant3, yesterday);
		createVote(person2, restaurant3, yesterday);
		chosen = restaurantService.makeChoiceToday();
		assertThat(chosen).as("restaurant 3 just has votes from yesterday")
			.isNotNull()
			.isNotEqualTo(restaurant3);
	}
	
	@Test
	public void receiveVote() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		Restaurant restaurant2 = createRestaurant("restaurant2");
		
		restaurantService.receive(new VoteBuilder()
			.from(person1)
			.votingIn(restaurant1)
			.on(Calendar.getInstance())
			.vote());
		assertThat(voteService.findAll()).hasSize(1);
		assertThat(restaurantService.findById(restaurant1.getId()).getVotes()).hasSize(1);
		
		restaurantService.receive(new VoteBuilder()
			.from(person2)
			.votingIn(restaurant1)
			.on(Calendar.getInstance())
			.vote());
		assertThat(voteService.findAll()).hasSize(2);
		assertThat(restaurantService.findById(restaurant1.getId()).getVotes()).hasSize(2);
		
		Vote lastVote = new VoteBuilder()
			.from(person1)
			.votingIn(restaurant2)
			.on(CalendarUtil.fromString("01/01/2015", CalendarUtil.DATE_FORMAT))
			.vote();
		restaurantService.receive(lastVote);
		assertThat(voteService.findAll()).hasSize(3);
		assertThat(restaurantService.findById(restaurant2.getId()).getVotes()).hasSize(1);
		
		restaurantService.receive(lastVote);
		assertThat(voteService.findAll()).as("this vote cannot be created as new because there is already a key with this values")
			.hasSize(3);
	}
	
	@Test
	public void restaurantAlreadyChosenReceivingVote() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		
		restaurantService.receive(new VoteBuilder()
			.from(person1)
			.votingIn(restaurant1)
			.on(Calendar.getInstance())
			.vote());
		restaurantService.makeChoiceToday();
		
		restaurant1 = restaurantService.findById(restaurant1.getId());
		assertThat(restaurant1.getChosenWeeks()).hasSize(1);
		assertThat(restaurant1.getVotes()).hasSize(1);
		
		try {
			restaurantService.receive(new VoteBuilder()
				.from(person2)
				.votingIn(restaurant1)
				.on(Calendar.getInstance())
				.vote());
		} catch (BusinessException be) {
			assertThat(be.getMessage()).isEqualTo(message.get("restaurant.already_chosen_this_week"));
		}
	}
	
}

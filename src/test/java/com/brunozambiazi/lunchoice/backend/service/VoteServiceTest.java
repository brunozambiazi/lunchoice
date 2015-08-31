
package com.brunozambiazi.lunchoice.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import java.util.Calendar;
import java.util.UUID;
import org.junit.Test;


public class VoteServiceTest extends BasicServiceTest {
	
	@Test
	public void findNoneByPersonOnNullWithVotes() {
		Person person = createPerson("person");
		Restaurant restaurant = createRestaurant("restaurant");
		createVote(person, restaurant, Calendar.getInstance());
		
		assertThat(voteService.findAll()).hasSize(1);
		
		assertThat(voteService.findByPersonOn(null, null)).isNull();
		assertThat(voteService.findByPersonOn(person, null)).isNull();
		assertThat(voteService.findByPersonOn(null, Calendar.getInstance())).isNull();
	}
	
	@Test
	public void findByPersonOn() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		Restaurant restaurant2 = createRestaurant("restaurant2");
		
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		Vote votePerson1Yesterday = createVote(person1, restaurant1, yesterday);
		Vote votePerson1Today = createVote(person1, restaurant2, Calendar.getInstance());
		Vote votePerson2Today = createVote(person2, restaurant1, Calendar.getInstance());
		
		assertThat(voteService.findByPersonOn(person1, yesterday))
			.isNotNull()
			.isEqualTo(votePerson1Yesterday);

		assertThat(voteService.findByPersonOn(person1, Calendar.getInstance()))
			.isNotNull()
			.isEqualTo(votePerson1Today);

		assertThat(voteService.findByPersonOn(person2, yesterday))
			.isNull();

		assertThat(voteService.findByPersonOn(person2, Calendar.getInstance()))
			.isNotNull()
			.isEqualTo(votePerson2Today);
	}
	
	@Test
	public void findToday() {
		assertThat(voteService.findToday()).isEmpty();
		
		int total = 10;
		
		for (int index = 0; index < total; index++) {
			Person person = createPerson(UUID.randomUUID().toString());
			Restaurant restaurant = createRestaurant(UUID.randomUUID().toString());
			createVote(person, restaurant, Calendar.getInstance());
		}
		assertThat(voteService.findToday())
			.hasSize(total)
			.isEqualTo(voteService.findAll());
		
		for (int index = 0; index < total; index++) {
			Person person = createPerson(UUID.randomUUID().toString());
			Restaurant restaurant = createRestaurant(UUID.randomUUID().toString());
			Calendar yesterday = Calendar.getInstance();
			yesterday.add(Calendar.DAY_OF_MONTH, -1);
			createVote(person, restaurant, yesterday);
		}
		assertThat(voteService.findToday()).hasSize(total);
		assertThat(voteService.findAll()).hasSize(total*2);
	}
	
	@Test
	public void mostVotedTodayWithoutVotes() {
		assertThat(voteService.mostVotedToday()).isNull();
		
		createRestaurant("restaurant");
		assertThat(voteService.mostVotedToday()).isNull();
	}
	
	@Test
	public void mostVotedToday() {
		Calendar yesterday = Calendar.getInstance();
		yesterday.add(Calendar.DAY_OF_MONTH, -1);
		
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Person person3 = createPerson("person3");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		Restaurant restaurant2 = createRestaurant("restaurant2");
		
		createVote(person1, restaurant1, yesterday);
		createVote(person2, restaurant2, yesterday);
		createVote(person3, restaurant1, yesterday);
		assertThat(voteService.findAll()).hasSize(3);
		assertThat(voteService.mostVotedToday()).isNull();
		
		createVote(person1, restaurant1, Calendar.getInstance());
		assertThat(voteService.mostVotedToday()).as("just restaurant 1 has votes")
			.isNotNull()
			.isEqualTo(restaurant1);

		createVote(person2, restaurant2, Calendar.getInstance());
		assertThat(voteService.mostVotedToday()).as("both restaurant 1 and restaurant 2 have been voted")
			.isNotNull()
			.isIn(restaurant1, restaurant2);
		
		createVote(person3, restaurant2, Calendar.getInstance());
		assertThat(voteService.mostVotedToday()).as("restaurant 2 has received more votes")
			.isNotNull()
			.isEqualTo(restaurant2);
	}
	
}

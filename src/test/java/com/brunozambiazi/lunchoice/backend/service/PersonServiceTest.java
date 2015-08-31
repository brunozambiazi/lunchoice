
package com.brunozambiazi.lunchoice.backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.config.AspectLimitVotingHourTest;
import java.util.Calendar;
import org.junit.Test;


public class PersonServiceTest extends BasicServiceTest {

	@Test
	public void canVoteNow() {
		assertThat(personService.canVoteNow()).isTrue();
	}
	
	@Test
	public void cantVoteFiveMinutesLater() {
		Calendar limit = Calendar.getInstance();
		limit.add(Calendar.MINUTE, -5);
		
		AspectLimitVotingHourTest.LIMIT = limit;
		assertThat(personService.canVoteNow()).isFalse();
	}
	
	@Test
	public void cantVoteTodayNull() {
		assertThat(personService.canVoteToday(null)).isFalse();
	}
	
	@Test
	public void cantVoteTodayInvalidPerson() {
		Person person = new Person();
		assertThat(personService.canVoteToday(person)).isFalse();
		
		person.setId("");
		assertThat(personService.canVoteToday(person)).isFalse();

		createPerson("person");

		person.setId("anything");
		assertThat(personService.canVoteToday(person)).isFalse();
	}
	
	@Test
	public void canVoteTodayWithExistingVotes() {
		Restaurant restaurant = createRestaurant("restaurant");
		
		createPerson("person");
		Person person = personService.findById("person@mailinator.com");
		assertThat(personService.canVoteToday(person)).isTrue();
		
		createVote(person, restaurant, Calendar.getInstance());
		assertThat(personService.canVoteToday(person)).isFalse();
	}
	
	@Test
	public void tryingToVotWithInvalidArguments() {
		try {
			personService.vote(null, null);
		} catch (Exception e) {
			assertThat(e).isInstanceOf(NullPointerException.class);
		}
		
		try {
			personService.vote(new Person(), new Restaurant());
		} catch (Exception e) {
			assertThat(e).isInstanceOf(BusinessException.class);
		}
		
		try {
			AspectLimitVotingHourTest.LIMIT = Calendar.getInstance();
			AspectLimitVotingHourTest.LIMIT.add(Calendar.MINUTE, -5);
			personService.vote(new Person(), new Restaurant());
		} catch (BusinessException be) {
			assertThat(be.getMessage()).isEqualTo(message.get("person.deny_vote_now"));
		}
		
		assertThat(voteService.findAll()).isEmpty();
	}
	
	@Test
	public void voting() {
		Person person1 = createPerson("person1");
		Person person2 = createPerson("person2");
		Restaurant restaurant1 = createRestaurant("restaurant1");
		Restaurant restaurant2 = createRestaurant("restaurant2");
		
		Calendar init = Calendar.getInstance();
		init.clear();
		createVote(person1, restaurant1, init);
		createVote(person2, restaurant2, init);
		assertThat(voteService.findAll()).hasSize(2);
		
		personService.vote(person1, restaurant1);
		assertThat(voteService.findByPersonOn(person1, Calendar.getInstance()))
			.as("person 1 voting first time today")
			.isNotNull();
		
		personService.vote(person2, restaurant2);
		assertThat(voteService.findByPersonOn(person2, Calendar.getInstance()))
			.as("person 2 voting first time today")
			.isNotNull();
		
		try {
			personService.vote(person1, restaurant2);
		} catch (BusinessException be) {
			assertThat(be.getMessage())
				.as("person 1 can't vote again today")
				.isEqualTo(message.get("person.already_voted_today"));
		}

		try {
			personService.vote(person2, restaurant2);
		} catch (BusinessException be) {
			assertThat(be.getMessage())
				.as("person 2 can't vote again today")
				.isEqualTo(message.get("person.already_voted_today"));
		}
		
		assertThat(voteService.findAll()).hasSize(4);
	}
	
}

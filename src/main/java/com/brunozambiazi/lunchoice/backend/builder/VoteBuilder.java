
package com.brunozambiazi.lunchoice.backend.builder;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;

import java.util.Calendar;
import java.util.Objects;


public final class VoteBuilder {

	private final Vote vote;

	
	public VoteBuilder() {
		vote = new Vote();
		vote.setId(new PersonalDailyVote());
	}
	
	
	public VoteBuilder from(Person person) {
		Objects.requireNonNull(person);
		vote.getId().setPerson(person);
		return this;
	}
	
	public VoteBuilder votingIn(Restaurant restaurant) {
		Objects.requireNonNull(restaurant);
		vote.setRestaurant(restaurant);
		return this;
	}
	
	public VoteBuilder on(Calendar date) {
		Objects.requireNonNull(date);
		vote.getId().setDateCalendar(date);
		return this;
	}
	
	public Vote vote() {
		return vote;
	}
	
}

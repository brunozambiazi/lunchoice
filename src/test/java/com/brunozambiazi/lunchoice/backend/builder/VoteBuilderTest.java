
package com.brunozambiazi.lunchoice.backend.builder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import java.util.Calendar;
import org.junit.Before;
import org.junit.Test;


public class VoteBuilderTest {

	private VoteBuilder builder;
	
	
	@Before
	public void config() {
		builder = new VoteBuilder();
		assertNotNull(builder);
		assertNotNull(builder.vote());
		assertNotNull(builder.vote().getId());
		assertNull(builder.vote().getId().getDate());
		assertNull(builder.vote().getId().getPerson());
		assertNull(builder.vote().getRestaurant());
	}
	
	@Test(expected = NullPointerException.class)
	public void fromNull() {
		builder.from(null);
	}
	
	@Test
	public void fromPerson() {
		builder.from(new Person());
		assertNotNull(builder.vote().getId().getPerson());
	}

	@Test(expected = NullPointerException.class)
	public void votingInNull() {
		builder.votingIn(null);
	}

	@Test
	public void votingInRestaurant() {
		builder.votingIn(new Restaurant());
		assertNotNull(builder.vote().getRestaurant());
	}

	@Test(expected = NullPointerException.class)
	public void onNull() {
		builder.on(null);
	}

	@Test
	public void onCalendar() {
		builder.on(Calendar.getInstance());
		assertNotNull(builder.vote().getId().getDate());
	}
	
}

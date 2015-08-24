
package com.brunozambiazi.lunchoice.backend.builder;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.lunchoice.backend.builder.VoteBuilder;
import java.util.Calendar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class VoteBuilderTest {

	private VoteBuilder builder;
	
	
	@Before
	public void config() {
		builder = new VoteBuilder();
		Assert.assertNotNull(builder);
		Assert.assertNotNull(builder.vote());
		Assert.assertNotNull(builder.vote().getId());
		Assert.assertNull(builder.vote().getId().getDate());
		Assert.assertNull(builder.vote().getId().getPerson());
		Assert.assertNull(builder.vote().getRestaurant());
	}
	
	@Test(expected = NullPointerException.class)
	public void fromNull() {
		builder.from(null);
	}
	
	@Test
	public void fromPerson() {
		builder.from(new Person());
		Assert.assertNotNull(builder.vote().getId().getPerson());
	}

	@Test(expected = NullPointerException.class)
	public void votingInNull() {
		builder.votingIn(null);
	}

	@Test
	public void votingInRestaurant() {
		builder.votingIn(new Restaurant());
		Assert.assertNotNull(builder.vote().getRestaurant());
	}

	@Test(expected = NullPointerException.class)
	public void onNull() {
		builder.on(null);
	}

	@Test
	public void onCalendar() {
		builder.on(Calendar.getInstance());
		Assert.assertNotNull(builder.vote().getId().getDate());
	}
	
}

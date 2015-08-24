
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.framework.service.BaseService;


public interface PersonService extends BaseService<Person, String> {

	/**
	 * 
	 * @param person
	 * @return
	 */
	boolean canVoteNow();
	
	/**
	 * 
	 * @param person
	 * @return
	 */
	boolean canVoteToday(Person person);
	
	/**
	 * 
	 * @param person
	 * @param restaurant
	 */
	void vote(Person person, Restaurant restaurant);
	
}

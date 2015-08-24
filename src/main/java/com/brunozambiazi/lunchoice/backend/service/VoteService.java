
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;

import com.brunozambiazi.framework.service.BaseService;
import java.util.Calendar;
import java.util.Collection;


public interface VoteService extends BaseService<Vote, PersonalDailyVote> {
	
	/**
	 * 
	 * @param person
	 * @param date
	 * @return
	 */
	Vote findByPersonOn(Person person, Calendar date);
	
	/**
	 * 
	 * @return
	 */
	Collection<Vote> findToday();
	
	/**
	 * 
	 * @return
	 */
	Calendar getLimitHourForVote();
	
	/**
	 * 
	 * @return
	 */
	Restaurant mostVotedToday();
	
}

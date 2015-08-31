
package com.brunozambiazi.lunchoice.backend.service.impl;

import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.service.BasicService;
import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.framework.util.CalendarUtil;
import com.brunozambiazi.lunchoice.backend.builder.VoteBuilder;
import com.brunozambiazi.lunchoice.backend.dao.PersonDao;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.backend.service.PersonService;
import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import com.brunozambiazi.lunchoice.backend.service.VoteService;
import java.util.Calendar;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class PersonServiceImpl extends BasicService<Person, String> implements PersonService {

	@Autowired
	@Getter(AccessLevel.PROTECTED)
	private PersonDao dao;
	
	@Autowired
	private Message message;
	
	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private VoteService voteService;

	
	@Override
	public boolean canVoteNow() {
		Calendar now = CalendarUtil.fromString(CalendarUtil.toString(Calendar.getInstance(), CalendarUtil.HOUR_FORMAT), CalendarUtil.HOUR_FORMAT);
		return now.before(voteService.getLimitHourForVote());
	}
	
	@Override
	public boolean canVoteToday(Person person) {
		if (person == null || person.getId() == null) {
			return false;
		}
		
		person = findById(person.getId());
		return (person != null && voteService.findByPersonOn(person, Calendar.getInstance()) == null);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void vote(Person person, Restaurant restaurant) {
		Objects.requireNonNull(person);
		Objects.requireNonNull(restaurant);
		
		if (! canVoteNow()) {
			throw new BusinessException(message.get("person.deny_vote_now"));
		}
		
		if (! canVoteToday(person)) {
			throw new BusinessException(message.get("person.already_voted_today"));
		}
		
		Vote vote = new VoteBuilder()
			.from(person)
			.votingIn(restaurant)
			.on(Calendar.getInstance())
			.vote();
		
		restaurantService.receive(vote);
	}

}

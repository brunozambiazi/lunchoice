
package com.brunozambiazi.lunchoice.backend.service.impl;

import com.brunozambiazi.lunchoice.backend.service.VoteService;

import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.backend.dao.VoteDao;
import com.brunozambiazi.framework.service.BasicService;
import com.brunozambiazi.framework.util.ArgumentKey;
import com.brunozambiazi.framework.util.CalendarUtil;
import com.brunozambiazi.framework.util.CollectionUtil;
import com.brunozambiazi.framework.util.MapUtil;
import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
public class VoteServiceImpl extends BasicService<Vote, PersonalDailyVote> implements VoteService {

	private static final String LIMIT = "11:30";
	
	@Autowired 
	@Getter(AccessLevel.PROTECTED)
	private VoteDao dao;
	
	
	@Override
	public Vote findByPersonOn(Person person, Calendar date) {
		Objects.requireNonNull(person);
		Objects.requireNonNull(date);
		
		PersonalDailyVote dailyVote = new PersonalDailyVote();
		dailyVote.setPerson(person);
		dailyVote.setDateCalendar(date);
		return getDao().findById(dailyVote);
	}
	
	@Override
	public Collection<Vote> findToday() {
		return getDao().findByDate(CalendarUtil.toString(Calendar.getInstance(), CalendarUtil.DATE_FORMAT));
	}
	
	@Override
	public Calendar getLimitHourForVote() {
		return CalendarUtil.fromString(LIMIT, CalendarUtil.HOUR_FORMAT);
	}
	
	@Override
	public Restaurant mostVotedToday() {
		Collection<Vote> votes = findToday();
		
		if (CollectionUtils.isEmpty(votes)) {
			return null;
		}
		
		return MapUtil.getMostRelevantKey(CollectionUtil.map(votes, new ArgumentKey<Vote, Restaurant>() {

			@Override public Restaurant getFrom(Vote arg) {
				return arg.getRestaurant();
			}
			
		}));
	}
	
}

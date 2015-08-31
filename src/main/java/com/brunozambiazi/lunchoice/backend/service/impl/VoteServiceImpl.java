
package com.brunozambiazi.lunchoice.backend.service.impl;

import com.brunozambiazi.framework.service.BasicService;
import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.framework.util.ArgumentKey;
import com.brunozambiazi.framework.util.CalendarUtil;
import com.brunozambiazi.framework.util.CollectionUtil;
import com.brunozambiazi.framework.util.MapUtil;
import com.brunozambiazi.lunchoice.backend.dao.VoteDao;
import com.brunozambiazi.lunchoice.backend.model.Person;
import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.backend.service.VoteService;
import java.util.Calendar;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


@Service
public class VoteServiceImpl extends BasicService<Vote, PersonalDailyVote> implements VoteService {

	@Autowired 
	@Getter(AccessLevel.PROTECTED)
	private VoteDao dao;
	
	@Autowired
	private Message message;
	
	@Value("${limit.hour.voting}")
	private String hour;
	
	
	@Override
	public Vote findByPersonOn(Person person, Calendar date) {
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
		Calendar time = CalendarUtil.fromString(hour, "HH:mm");
		
		if (time == null) {
			throw new IllegalArgumentException(message.get("vote.limit_wrong_configured"));
		}
		
		Calendar limit = Calendar.getInstance();
		limit.set(Calendar.HOUR, time.get(Calendar.HOUR));
		limit.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
		limit.set(Calendar.SECOND, 0);
		limit.set(Calendar.MILLISECOND, 0);
		return limit;
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

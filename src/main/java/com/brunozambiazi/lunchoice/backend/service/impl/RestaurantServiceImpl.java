
package com.brunozambiazi.lunchoice.backend.service.impl;

import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import com.brunozambiazi.lunchoice.backend.service.VoteService;

import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.backend.dao.RestaurantDao;
import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.service.BasicService;
import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.framework.util.CalendarUtil;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RestaurantServiceImpl extends BasicService<Restaurant, String> implements RestaurantService {

	@Autowired
	@Getter(AccessLevel.PROTECTED)
	private RestaurantDao dao;
	
	@Autowired
	private Message message;
	
	@Autowired
	private VoteService voteService;
	
	
	@Override
	public Restaurant findAnyone() {
		return getDao().findAnyone();
	}
	
	@Override
	@Transactional(readOnly = false)
	public Restaurant makeChoiceToday() {
		Restaurant chosen = voteService.mostVotedToday();
		
		if (chosen == null) {
			chosen = findAnyone();
		}
		
		if (chosen.getChosenWeeks() == null) {
			chosen.setChosenWeeks(new HashSet<String>());
		}
		
		chosen.getChosenWeeks().add(CalendarUtil.toString(Calendar.getInstance(), CalendarUtil.WEEK_FORMAT));
		return save(chosen);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void receive(Vote vote) {
		Objects.requireNonNull(vote);
		Objects.requireNonNull(vote.getRestaurant());
		Objects.requireNonNull(vote.getId());
		Objects.requireNonNull(vote.getId().getDateCalendar());
		 
		String thisWeek = CalendarUtil.toString(vote.getId().getDateCalendar(), CalendarUtil.WEEK_FORMAT);
		Restaurant restaurant = vote.getRestaurant();
		
		if (restaurant.getChosenWeeks() == null) {
			restaurant.setChosenWeeks(new HashSet<String>());
		}
		
		if (restaurant.getChosenWeeks().contains(thisWeek)) {
			throw new BusinessException(message.get("restaurant.already_chosen_this_week"));
		}
		
		voteService.save(vote);
	}

}

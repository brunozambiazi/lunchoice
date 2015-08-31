
package com.brunozambiazi.lunchoice.backend.service.impl;

import com.brunozambiazi.framework.exception.BusinessException;
import com.brunozambiazi.framework.service.BasicService;
import com.brunozambiazi.framework.spring.Message;
import com.brunozambiazi.framework.util.CalendarUtil;
import com.brunozambiazi.framework.util.CollectionUtil;
import com.brunozambiazi.framework.util.Predicate;
import com.brunozambiazi.lunchoice.backend.dao.RestaurantDao;
import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;
import com.brunozambiazi.lunchoice.backend.service.RestaurantService;
import com.brunozambiazi.lunchoice.backend.service.VoteService;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;


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
	public List<Restaurant> findAll() {
		List<Restaurant> restaurants = super.findAll();
		
		if (! CollectionUtils.isEmpty(restaurants)) {
			sumVotesToday(restaurants);
			Collections.sort(restaurants);
		}
		
		return restaurants;
	}
	
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
		
		if (chosen == null) {
			throw new BusinessException(message.get("restaurant.there_are_no_restaurants"));
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
	
	private void sumVotesToday(Collection<Restaurant> restaurants) {
		if (CollectionUtils.isEmpty(restaurants)) {
			return;
		}
		
		Predicate<Vote> predicate = new Predicate<Vote>() {

			@Override public boolean evaluate(Vote element) {
				return CalendarUtil.equalsDate(element.getId().getDateCalendar(), Calendar.getInstance());
			}
			
		};
		
		for (Restaurant restaurant : restaurants) {
			restaurant.setVotesToday(CollectionUtil.find(restaurant.getVotes(), predicate).size());
		}
	}

}

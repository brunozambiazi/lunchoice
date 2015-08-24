
package com.brunozambiazi.lunchoice.scheduler;

import com.brunozambiazi.lunchoice.backend.service.RestaurantService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TodayChoiceWinnerScheduler {

	@Autowired
	private RestaurantService restaurantService;
	
	
	@Scheduled(cron = "0 35 11 * * MON-FRI")
	public void run() {
		restaurantService.makeChoiceToday();
	}
	
}

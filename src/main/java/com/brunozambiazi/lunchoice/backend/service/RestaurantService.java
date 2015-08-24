
package com.brunozambiazi.lunchoice.backend.service;

import com.brunozambiazi.lunchoice.backend.model.Restaurant;
import com.brunozambiazi.lunchoice.backend.model.Vote;

import com.brunozambiazi.framework.service.BaseService;


public interface RestaurantService extends BaseService<Restaurant, String> {

	/**
	 * 
	 * @return
	 */
	Restaurant findAnyone();
	
	/**
	 * 
	 * @return
	 */
	Restaurant makeChoiceToday();
	
	/**
	 * 
	 * @param vote
	 */
	void receive(Vote vote);
	
}


package com.brunozambiazi.lunchoice.backend.dao;

import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.framework.dao.BaseDao;


public interface RestaurantDao extends BaseDao<Restaurant, String> {

	Restaurant findAnyone();
	
}

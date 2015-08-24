
package com.brunozambiazi.lunchoice.backend.dao.impl;

import com.brunozambiazi.lunchoice.backend.model.Restaurant;

import com.brunozambiazi.lunchoice.backend.dao.RestaurantDao;
import com.brunozambiazi.framework.dao.BasicDao;
import org.springframework.stereotype.Repository;


@Repository
public class RestaurantDaoImpl extends BasicDao<Restaurant, String> implements RestaurantDao {

	public RestaurantDaoImpl() {
		super(Restaurant.class);
	}
	
	
	@Override
	public Restaurant findAnyone() {
		return (Restaurant)getSession().createQuery("from Restaurant")
			.setMaxResults(1)
			.uniqueResult();
	}
	
}

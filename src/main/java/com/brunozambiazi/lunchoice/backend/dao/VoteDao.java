
package com.brunozambiazi.lunchoice.backend.dao;

import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Vote;

import com.brunozambiazi.framework.dao.BaseDao;
import java.util.Collection;


public interface VoteDao extends BaseDao<Vote, PersonalDailyVote> {

	Collection<Vote> findByDate(String date);
	
}

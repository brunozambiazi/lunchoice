
package com.brunozambiazi.lunchoice.backend.dao.impl;

import com.brunozambiazi.lunchoice.backend.model.PersonalDailyVote;
import com.brunozambiazi.lunchoice.backend.model.Vote;

import com.brunozambiazi.lunchoice.backend.dao.VoteDao;
import com.brunozambiazi.framework.dao.BasicDao;
import java.util.Collection;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unchecked")
@Repository
public class VoteDaoImpl extends BasicDao<Vote, PersonalDailyVote> implements VoteDao {

	public VoteDaoImpl() {
		super(Vote.class);
	}
	

	@Override
	public Collection<Vote> findByDate(String date) {
		return getSession().createQuery("from Vote where id.date = :date")
			.setParameter("date", date)
			.list();
	}

}

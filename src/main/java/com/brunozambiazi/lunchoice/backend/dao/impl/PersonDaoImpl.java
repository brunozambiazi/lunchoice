
package com.brunozambiazi.lunchoice.backend.dao.impl;

import com.brunozambiazi.lunchoice.backend.model.Person;

import com.brunozambiazi.lunchoice.backend.dao.PersonDao;
import com.brunozambiazi.framework.dao.BasicDao;
import org.springframework.stereotype.Repository;


@Repository
public class PersonDaoImpl extends BasicDao<Person, String> implements PersonDao {

	public PersonDaoImpl() {
		super(Person.class);
	}

}

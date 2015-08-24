
package com.brunozambiazi.framework.dao;

import com.brunozambiazi.framework.model.BaseEntity;
import java.io.Serializable;
import java.util.Collection;


public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable> {

	Collection<T> findAll();
	
	T findById(ID id);
	
	void remove(T entity);
	
	T save(T entity);
	
}

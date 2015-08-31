
package com.brunozambiazi.framework.dao;

import com.brunozambiazi.framework.model.BaseEntity;
import java.io.Serializable;
import java.util.List;


public interface BaseDao<T extends BaseEntity<ID>, ID extends Serializable> {

	List<T> findAll();
	
	T findById(ID id);
	
	void remove(T entity);
	
	T save(T entity);
	
}

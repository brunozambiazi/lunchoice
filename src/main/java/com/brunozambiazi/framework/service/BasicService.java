
package com.brunozambiazi.framework.service;

import com.brunozambiazi.framework.dao.BaseDao;
import com.brunozambiazi.framework.model.BaseEntity;
import java.io.Serializable;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly = true)
public abstract class BasicService<T extends BaseEntity<ID>, ID extends Serializable> implements BaseService<T, ID> {

	@Override
	public List<T> findAll() {
		return getDao().findAll();
	}
	
	@Override
	public T findById(ID id) {
		return getDao().findById(id);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void remove(T entity) {
		getDao().remove(entity);
	}
	
	@Override
	@Transactional(readOnly = false)
	public T save(T entity) {
		return getDao().save(entity);
	}

	
	protected abstract BaseDao<T, ID> getDao();

}

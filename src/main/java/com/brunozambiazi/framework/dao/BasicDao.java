
package com.brunozambiazi.framework.dao;

import com.brunozambiazi.framework.model.BaseEntity;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;


@SuppressWarnings("unchecked")
public abstract class BasicDao<T extends BaseEntity<ID>, ID extends Serializable> implements BaseDao<T, ID> {

	private final Class<T> entityClass;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	protected BasicDao(Class<T> entityClass) {
		this.entityClass = entityClass;
	}


	@Override
	public List<T> findAll() {
		return getSession().createQuery(String.format("from %s", entityClass.getSimpleName())).list();
	}
	
	@Override
	public T findById(ID id) {
		return entityManager.find(entityClass, id);
	}

	protected Session getSession() {
		return (Session)entityManager.getDelegate();
	}
	
	@Override
	public void remove(T entity) {
		entityManager.remove(entityManager.find(entityClass, entity.getId()));
	}
	
	@Override
	public T save(T entity) {
		return entityManager.merge(entity);
	}
	
}

package com.jialu.minios.utility;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.jialu.minios.base.process.UtilProcess;
import com.jialu.minios.vo.MiniPair;
import com.jialu.minios.vo.MiniQuery;

import io.dropwizard.hibernate.AbstractDAO;

public abstract class MiniDao<T extends MiniModel> extends AbstractDAO<T> {

	public MiniDao(SessionFactory factory) {
		super(factory);
	}

	public T findById(Integer id) {
		return get(id);
	}

	public T save(T entity) throws IllegalAccessException, InvocationTargetException {
		if (entity.getId() == null || entity.getId() == 0) {
			entity.setId(null);
			if(entity.getCuser() == null){
				entity.setCuser(0);
			}
			entity.setCtime(new Timestamp(System.currentTimeMillis()));
			entity.setDeleted(0);
			if(entity.getUuser() == null){
				entity.setUuser(0);
			}
			entity.setUtime(new Timestamp(System.currentTimeMillis()));
			return persist(entity);
		}

		T exsit = findById(entity.getId());
		MiniCopyBean copyBean = new MiniCopyBean();
		copyBean.copyProperties(exsit, entity);
		if(entity.getUuser() == null){
			entity.setUuser(0);
		}
		exsit.setUuser(entity.getUuser());
		exsit.setUtime(new Timestamp(System.currentTimeMillis()));
		persist(exsit);
		return entity;
	}

	public long delete(T entity) {
		entity.setDeleted(1);
		entity.setUuser(0);
		entity.setUtime(new Timestamp(System.currentTimeMillis()));
		return persist(entity).getId();
	}

	public long count(Class<T> type, MiniQuery query) {
		Criteria crit = currentSession().createCriteria(type);
		crit.setProjection(Projections.rowCount());
		crit.add(Restrictions.eq("deleted", 0));
		if(query.getParams() != null){
			for (MiniPair param : query.getParams()) {
				Object value = UtilProcess.castValue(query.getName(), param);
				crit.add(Restrictions.eq(param.getKey(), value));
			}
		}
		return (long) crit.uniqueResult();
	}

	public List<T> findAllByQuery(MiniQuery mquery, Integer maxCount) {
		if (mquery.getName() == null) {
			return null;
		}
		Query query = namedQuery(mquery.getName());

		if (mquery.getLimit() == null) {
			query.setMaxResults(maxCount);
		} else if (mquery.getLimit() > maxCount) {
			query.setMaxResults(maxCount);
		} else {
			query.setMaxResults(mquery.getLimit());
		}

		if (mquery.getOffset() == null) {
			query.setFirstResult(0);
		} else {
			query.setFirstResult(mquery.getOffset());
		}

		if (mquery.getParams() != null) {
			for (MiniPair param : mquery.getParams()) {
				Object value = UtilProcess.castValue(mquery.getName(), param);
				query.setParameter(param.getKey(), value);
			}
		}

		return list(query);
	}

	public T findOneByQuery(MiniQuery mquery) {
		Query query = namedQuery(mquery.getName());
		if (mquery.getName() == null) {
			return null;
		}

		if (mquery.getParams() != null) {
			for (MiniPair param : mquery.getParams()) {
				query.setParameter(param.getKey(), param.getValue());
			}
		}

		List<T> list = list(query);
		if (list == null) {
			return null;
		}
		if (list.size() == 0) {
			return null;
		}
		return list.get(0);
	}
}

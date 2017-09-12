package com.jialu.sawa.utility;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.jialu.sawa.vo.MiniQuery;
import com.jialu.sawa.vo.OperatorResult;
import com.jialu.sawa.vo.OperatorRole;

public abstract class MiniCrudResource<T extends MiniModel, TD extends MiniDao<T>> extends MiniResource{
	
	public abstract TD getDao();
	
	public abstract Class<T> getModelT();
	
	public MiniCrudResource(MiniBean config) {
		super(config);
	}
	
	public OperatorResult<T> _getById(Integer id) {
		OperatorResult<T> or = new OperatorResult<T>();
		try{
			or.setCode(OpResult.OK.name());
			or.setData(getDao().findById(id));
		}catch(org.hibernate.MappingException ex){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("get error");
			LOGGER.error("getById", ex);
		}
		return or;
	}
	
	public OperatorResult<T> _getByQuery(MiniQuery query) {
		OperatorResult<T> or = new OperatorResult<T>();
		try{
			or.setCode(OpResult.OK.name());
			or.setData(getDao().findOneByQuery(query));
		}catch(org.hibernate.MappingException ex){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("query error");
			LOGGER.error("getByQuery", ex);
		}
		return or;
	}

	public OperatorResult<T> _delete(OperatorRole role, Integer id) {
		OperatorResult<T> or = new OperatorResult<T>();
		try{
			or.setCode(OpResult.OK.name());
			T data = getDao().findById(id);
			if (data == null) {
				or.setCode(OpResult.ERROR.name());
				or.setMsg("data not found");
			} else {
				data.setUuser(role.getUser().getId());
				getDao().delete(data);
				or.setData(data);
			}
		}catch(org.hibernate.MappingException ex){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("delete error");
			LOGGER.error("delete", ex);
		}
		return or;
	}

	public OperatorResult<T> _save(OperatorRole role, T json) {
		OperatorResult<T> or = new OperatorResult<T>();
		try{
			or.setCode(OpResult.OK.name());
			if(json.getId() == null || json.getId() == 0){
				json.setCuser(role.getUser().getId());
			}else{
				json.setUuser(role.getUser().getId());
			}
			or.setData(getDao().save(json));
		}catch(org.hibernate.MappingException ex){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("save error");
			LOGGER.error("save", ex);
		} catch (IllegalAccessException ex) {
			or.setCode(OpResult.ERROR.name());
			or.setMsg("save error");
			LOGGER.error("save", ex);
		} catch (InvocationTargetException ex) {
			or.setCode(OpResult.ERROR.name());
			or.setMsg("save error");
			LOGGER.error("save", ex);
		} catch (NoSuchMethodException ex) {
			or.setCode(OpResult.ERROR.name());
			or.setMsg("save error");
			LOGGER.error("save", ex);
		}
		return or;
	}

	public OperatorResult<List<T>> _list(MiniQuery query) {
		OperatorResult<List<T>> or = new OperatorResult<List<T>>();
		try{
			or.setCode(OpResult.OK.name());
			or.setData(getDao().findAllByQuery(query, Integer.parseInt(config.getMaxPost())));
			or.setTotal(getDao().count(getModelT(), query));
		}catch(org.hibernate.MappingException ex){
			or.setCode(OpResult.ERROR.name());
			or.setMsg("list error");
			LOGGER.error("list", ex);
		}
		return or;
	}
}

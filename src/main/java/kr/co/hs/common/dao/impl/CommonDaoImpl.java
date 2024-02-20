package kr.co.hs.common.dao.impl;

import java.util.List;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dao.support.BaseDaoSupport;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class CommonDaoImpl extends BaseDaoSupport implements ICommonDao {
	
	public int selectCount(String query, Object search) throws DataAccessException {
		return (Integer)getSqlSession().selectOne(query, search);
	}
	
	@SuppressWarnings({ "unchecked"})
	public <E> List<E> selectList(String query, Object search) throws DataAccessException {
		return (List<E>)getSqlSession().selectList(query, search);
	}
	public <E> List<E> selectAll(String query) throws DataAccessException {
		return (List<E>)getSqlSession().selectList(query);
	}

	public <E> List<E> selectPageList(String query, Object search) throws DataAccessException {
		return selectList(query, search);
	}

	@SuppressWarnings("unchecked")
	public <E> E selectOne(String query, Object search) throws DataAccessException {
		return (E)getSqlSession().selectOne(query, search);
	}
	
	public Object insertData(String query, Object model) throws DataAccessException {
		getSqlSession().insert(query, model);
		return model;
	}

	public int insertDataInt(String query, Object model) throws DataAccessException {
		getSqlSession().insert(query, model);
		return 1;
	}
	
	public int updateData(String query, Object model) throws DataAccessException {
		return getSqlSession().update(query, model);
	}
	
	public int updateData(String query) throws DataAccessException {
		return getSqlSession().update(query);
	}
	
	public int deleteData(String query, Object search) throws DataAccessException {
		return getSqlSession().delete(query, search);
	}
	
	public <T> List<T> getDataset(List<List<Object>> datasets, int index){
		return (List<T>) datasets.get(index);
	}
}

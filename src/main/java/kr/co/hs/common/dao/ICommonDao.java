package kr.co.hs.common.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface ICommonDao {

	public int selectCount(String query, Object search) throws DataAccessException;
	
	public <E> E selectOne(String query, Object search) throws DataAccessException;
	public <E> List<E> selectList(String query, Object search) throws DataAccessException;
	public <E> List<E> selectAll(String query) throws DataAccessException;
	public <E> List<E> selectPageList(String query, Object search) throws DataAccessException;

	public Object insertData(String query, Object model) throws DataAccessException;
	public int insertDataInt(String query, Object model) throws DataAccessException;

	public int updateData(String query, Object model) throws DataAccessException;
	public int updateData(String query) throws DataAccessException;
	
	public int deleteData(String query, Object search) throws DataAccessException;
}

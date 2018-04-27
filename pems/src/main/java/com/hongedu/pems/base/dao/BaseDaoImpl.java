package com.hongedu.pems.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.hongedu.pems.base.dao.expression.Exp;


/**
 * dao通用实现类
 * @author zyb
 * @param <T>
 */
@Component
public class BaseDaoImpl<T> implements BaseDao<T> {

	private final static Logger logger= LoggerFactory.getLogger(BaseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void save(T entity) {
		String sql = BaseDaoUtil.makeSql(entity, BaseDaoUtil.SQL_INSERT);
		Object[] args = BaseDaoUtil.setArgs(entity, BaseDaoUtil.SQL_INSERT);
		jdbcTemplate.update(sql.toString(), args);
	}
	
	public int saveToPK(T entity) {
		String sql = BaseDaoUtil.makeSql(entity, BaseDaoUtil.SQL_INSERT);
		Object[] args = BaseDaoUtil.setArgs(entity, BaseDaoUtil.SQL_INSERT);
		int[] types = BaseDaoUtil.getColumnTypes(entity);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(sql, types);
		pscFactory.setReturnGeneratedKeys(true); // 返回自增主键
		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(args);
		
		jdbcTemplate.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
	}
	
	public int saveToPK(String sql, int[] types, Object[] args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		PreparedStatementCreatorFactory pscFactory = new PreparedStatementCreatorFactory(sql, types);
		pscFactory.setReturnGeneratedKeys(true); // 返回自增主键
		PreparedStatementCreator psc = pscFactory.newPreparedStatementCreator(args);
		jdbcTemplate.update(psc, keyHolder);
		return keyHolder.getKey().intValue();
	}

	public void delete(Class<T> entityClass, Serializable id) {
		String tableName = BaseDaoUtil.getTableProperties(entityClass, "name");
		String pk = BaseDaoUtil.getTableProperties(entityClass, "pk");
		pk = BaseDaoUtil.lowerStrToUnderline(pk);
		String sql = " DELETE FROM " + tableName + " WHERE " + pk + "=?";
		jdbcTemplate.update(sql, id);
	}
	
	public void delete(Class<T> entityClass, List<Exp> expList ){
		StringBuffer deleteSql = new StringBuffer();
		String mainTable = BaseDaoUtil.getTableProperties(entityClass, "name");
		String alias = BaseDaoUtil.getAlias(entityClass);
		deleteSql.append("delete ").append(alias).append(" from ").append(mainTable).append(" ").append(alias).append(" ").append(BaseDaoUtil.genExpSql(expList));
		logger.debug(deleteSql.toString());
		jdbcTemplate.update(deleteSql.toString());
	}
	
	public void executeBySql(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void updateBySql(String sql) {
		jdbcTemplate.update(sql);
	}
	
	public List<Map<String, Object>> selectBySql(String sql) {
		return jdbcTemplate.queryForList(sql);
	}
	
	public T findById(Class<T> entityClass, Serializable id) {
		String pk = BaseDaoUtil.getTableProperties(entityClass, "pk");
		pk = BaseDaoUtil.lowerStrToUnderline(pk);
		String tableName = BaseDaoUtil.getTableProperties(entityClass, "name");
		String sql = "SELECT * FROM " + tableName + " WHERE " + pk + "=?";
		RowMapper<T> rowMapper = (RowMapper<T>) BeanPropertyRowMapper.newInstance(entityClass);
		List<T> resultList = jdbcTemplate.query(sql, rowMapper, id);
		return (resultList.isEmpty()) ? null : resultList.get(0);
	}

	public List<T> findListBySql(Class<T> entityClass, String sql) {
		return findListBySql(entityClass, sql, 0, Integer.MAX_VALUE);
	}

	public List<T> findListBySql(Class<T> entityClass, String sql, int pageNo, int pageSize) {
		sql += " limit " + pageNo + "," + pageSize;
		logger.debug(sql);
		System.out.println(sql);
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql);
		List<T> list = BaseDaoUtil.mapToBean(entityClass, mapList);
		return list;
	}

	public int findCountBySql(Class<T> entityClass, String sql) {
		logger.debug(sql);
		@SuppressWarnings("deprecation")
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	
	public List<T> findList(Class<T> entityClass, List<Exp> expList) {
		return findListBySql(entityClass, BaseDaoUtil.genQuerySqlByBean("", entityClass, expList), 0, Integer.MAX_VALUE);
	}
	
	public List<T> findList(Class<T> entityClass, List<Exp> expList, int pageNo, int pageSize) {
		return findListBySql(entityClass, BaseDaoUtil.genQuerySqlByBean("", entityClass, expList), pageNo, pageSize);
	}
	
	public int findCount(Class<T> entityClass, List<Exp> expList) {
		return findCountBySql(entityClass, BaseDaoUtil.genCountSqlByBean(entityClass, expList));
	}

	@Override
	public void update(T entity) {
		String sql = BaseDaoUtil.makeSql(entity, BaseDaoUtil.SQL_UPDATE);
		Object[] args = BaseDaoUtil.setArgs(entity, BaseDaoUtil.SQL_UPDATE);
		jdbcTemplate.update(sql, args);
	}

	@Override
	public List<T> findList(String result, Class<T> entityClass, List<Exp> expList, int pageNo, int pageSize) {
		return findListBySql(entityClass, BaseDaoUtil.genQuerySqlByBean(result, entityClass, expList), pageNo, pageSize);
	}
	


}
package com.hongedu.pems.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hongedu.pems.base.dao.expression.Exp;


/**
 * dao接口
 * @author zyb
 *
 * @param <T>
 */
public interface BaseDao<T> {
	
	/**
	 * 插入对象
	 * @param entity 实体对象
	 */
	public void save(T entity);

	/**
	 * 插入对象
	 * @param entity
	 * @return 主键
	 */
	public int saveToPK(T entity);

	
	/**
	 * 修改对象
	 * @param entity 实体对象
	 */
	public void update(T entity);

	/**
	 * 删除对象
	 * @param entityClass 实体类型
	 * @param id 主键
	 */
	public void delete(Class<T> entityClass, Serializable id);

	/**
	 * 删除通过表达式
	 * @param entityClass 实体类型
	 * @param expList 表达式
	 */
	public void delete(Class<T> entityClass, List<Exp> expList );
	
	/**
	 * sql函数执行操作
	 * @param sql sql语句
	 */
	public void executeBySql(String sql);

	/**
	 * 原生sql更新操作
	 * @param sql sql语句
	 */
	public void updateBySql(String sql);
	
	/**
	 * sql查询操作
	 * @param sql sql语句
	 * @return map集合
	 */
	public List<Map<String, Object>> selectBySql(String sql);
	
	/**
	 * 根据主键查询对象
	 * @param entityClass 实体类型
	 * @param id 主键
	 * @return 对象集合
	 */
	public T findById(Class<T> entityClass, Serializable id);

	/**
	 * 根据sql查询对象集合
	 * @param entityClass 实体类型
	 * @param sql sql语句
	 * @return 对象集合
	 */
	public List<T> findListBySql(Class<T> entityClass, String sql);

	/**
	 * 根据sql查询对象集合（带分页）
	 * @param entityClass 实体类型
	 * @param sql sql语句
	 * @param pageNo 当前页
	 * @param pageSize 分页数
	 * @return 对象集合
	 */
	public List<T> findListBySql(Class<T> entityClass, String sql, int pageNo, int pageSize);

	/**
	 * 根据sql查询集合数量
	 * @param entityClass 实体类型
	 * @param sql sql语句
	 * @return 集合数量
	 */
	public int findCountBySql(Class<T> entityClass, String sql);
	
	/**
	 * 根据表达式查询对象集合
	 * @param entityClass 实体类型
	 * @param expList 表达式集合
	 * @return 对象集合
	 */
	public List<T> findList(Class<T> entityClass, List<Exp> expList);
	
	/**
	 * 根据表达式查询对象集合（带分页）
	 * @param entityClass 实体类型
	 * @param expList 表达式集合
	 * @param pageNo 当前页
	 * @param pageSize 分页数
	 * @return 对象集合
	 */
	public List<T> findList(Class<T> entityClass, List<Exp> expList, int pageNo, int pageSize);
	/**
	 * 根据表达式查询对象集合（带分页）
	 * @param result 查询结果集
	 * @param entityClass 实体类型
	 * @param expList 表达式集合
	 * @param pageNo 当前页
	 * @param pageSize 分页数
	 * @return 对象集合
	 * @return
	 */
	public List<T> findList(String result, Class<T> entityClass, List<Exp> expList, int pageNo, int pageSize);

	/**
	 * 根据表达式查询集合数量
	 * @param entityClass 实体类型
	 * @param expList 表达式集合
	 * @return 集合数量
	 */
	public int findCount(Class<T> entityClass, List<Exp> expList);
}

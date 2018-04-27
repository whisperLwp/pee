package com.hongedu.pems.base.dao.expression;

/**
 * 自定义查询表达式
 * @author zyb
 */
public interface Exp {
	/**
	 * 根据表别名生成sql
	 * @param alis 表别名
	 * @return
	 */
	public String genExpSql(String alis); 
	/**
	 * 获取表达式的实体类型
	 * @return
	 */
	public Class<?> genExpClass(); 
}

package com.hongedu.pems.base.dao.expression.impl;

import com.hongedu.pems.base.dao.BaseDaoUtil;
import com.hongedu.pems.base.dao.expression.Exp;

/**
 * sql排序表达式
 * @author zyb
 *
 */
public class OrderExp implements Exp{
	
	private String column;
	
	private String order;

	private Class<?> entityClass;
	/**
	 * sql排序表达式
	 * @param column 列名
	 * @param order 排序符，如desc、asc
	 */
	public OrderExp(Class<?> entityClass, String column, String order) {
		super();
		this.entityClass = entityClass;
		this.column = column;
		this.order = order;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	@Override
	public String genExpSql(String alis) {
		String tableAlis = BaseDaoUtil.getTableProperties(entityClass, "name");
		return " " + alis + "." + column + " " + order + ",";
	}
	
	@Override
	public Class<?> genExpClass() {
		return this.entityClass;
	}
}

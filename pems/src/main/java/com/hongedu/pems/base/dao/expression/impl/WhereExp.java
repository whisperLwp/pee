package com.hongedu.pems.base.dao.expression.impl;

import java.io.Serializable;

import com.hongedu.pems.base.dao.BaseDaoUtil;
import com.hongedu.pems.base.dao.expression.Exp;

/**
 * sql条件表达式
 * @author zyb
 *
 */
public class WhereExp implements Exp{
	
	
	
	private String operator;
	private String column;
	private Serializable value;
	private Class<?> entityClass;
	
	/**
	 * sql条件表达式
	 * @param column 字段名
	 * @param operator 操作符，如=，<>,like等
	 * @param value  字段值，如'condition'、condition、'%condition%'
	 */
	public  WhereExp(Class<?> entityClass, String column, String operator, Serializable value) {
		super();
		
		this.entityClass = entityClass;
		this.column = column;
		this.value = value;
		this.operator = operator;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public Serializable getValue() {
		return value;
	}

	public void setValue(Serializable value) {
		this.value = value;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Override
	public String genExpSql(String alis) {
		if(this.value == null){
			return "";
		}
		if(this.value instanceof String){
			String strVal = (String) this.value;
			if(strVal.equals("") || strVal.equals("%%") || strVal.equals("%")){
				return "";
			}
			this.value = strVal;
		}
//		String tableAlis = BaseDaoUtil.getTableProperties(entityClass, "name");
		return " and " + alis + "." + column + " " + operator + " '" + value + "'";
	}

	@Override
	public Class<?> genExpClass() {
		return this.entityClass;
	}
	
}

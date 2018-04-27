package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_sys_dept 表对应实体
 * 2018/01/31 02:37:13
 */
@Table(name="el_sys_dept", pk="deptId")
public class Dept extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**部门id*/
	private java.lang.Integer deptId;
	/**部门名称*/
	private java.lang.String deptName;
	/**备注*/
	private java.lang.String remark;
	/**父级部门id*/
	private java.lang.String pDeptId;
	/**部门负责人id*/
	private java.lang.Integer deptUserId;
	/** 部门编号 **/ 
	private String deptCode; 

	@ManyToOne(joinKey="dept_user_id")
	private Employee employee;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}


	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	
	public void setDeptName(java.lang.String value) {
		this.deptName = value;
	}
	
	public java.lang.String getDeptName() {
		return deptName;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return remark;
	}
	

	public java.lang.Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(java.lang.Integer deptId) {
		this.deptId = deptId;
	}

	public java.lang.String getpDeptId() {
		return pDeptId;
	}

	public void setpDeptId(java.lang.String pDeptId) {
		this.pDeptId = pDeptId;
	}

	public void setDeptUserId(java.lang.Integer value) {
		this.deptUserId = value;
	}
	
	public java.lang.Integer getDeptUserId() {
		return deptUserId;
	}
}



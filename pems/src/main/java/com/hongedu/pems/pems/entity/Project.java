package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_project 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_project", pk="projectId")
public class Project extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**项目id*/
	private java.lang.Integer projectId;
	/**项目名称*/
	private java.lang.String projectName;
	/**项目状态：1未开始、2已开始、3未结束、4已结束*/
	private java.lang.Integer status;
	/**创建人*/
	private java.lang.Integer creator;
	
	private String deptCode;
	
	@ManyToOne(joinKey="creator")
	private Employee employee;
	
	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return projectId;
	}
	public void setProjectName(java.lang.String value) {
		this.projectName = value;
	}
	
	public java.lang.String getProjectName() {
		return projectName;
	}
	
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public void setCreator(java.lang.Integer value) {
		this.creator = value;
	}
	
	public java.lang.Integer getCreator() {
		return creator;
	}
}



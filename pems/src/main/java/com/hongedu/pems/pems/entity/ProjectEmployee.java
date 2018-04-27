package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import org.apache.ibatis.annotations.Insert;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_project_employee 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_project_employee", pk="projectId")
public class ProjectEmployee extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**id*/
	private java.lang.Integer projectEmployeeId;
	/**项目id*/
	private java.lang.Integer projectId;
	/**员工id*/
	private java.lang.Integer employeeId;
	
	@Instant
	private String realName;
	
	@ManyToOne(joinKey="employee_id")
	private Employee employee;
	
	@ManyToOne(joinKey="project_id")
	private Project project;
	
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
	public void setEmployeeId(java.lang.Integer value) {
		this.employeeId = value;
	}
	
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}

	public java.lang.Integer getProjectEmployeeId() {
		return projectEmployeeId;
	}

	public void setProjectEmployeeId(java.lang.Integer projectEmployeeId) {
		this.projectEmployeeId = projectEmployeeId;
	}
}



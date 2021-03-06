package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_task_project_employee 表对应实体
 * 2018/02/24 02:40:05
 */
@Table(name="el_bp_task_project_employee", pk="taskProjectEmployeeId")
public class TaskProjectEmployee extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/***/
	private java.lang.Integer taskProjectEmployeeId;
	/**项目Id*/
	private java.lang.Integer projectId;
	/**员工Id*/
	private java.lang.Integer employeeId;
	/**考评人物Id*/
	private java.lang.Integer normTaskId;
	
	public void setTaskProjectEmployeeId(java.lang.Integer value) {
		this.taskProjectEmployeeId = value;
	}
	
	public java.lang.Integer getTaskProjectEmployeeId() {
		return taskProjectEmployeeId;
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
	public void setNormTaskId(java.lang.Integer value) {
		this.normTaskId = value;
	}
	
	public java.lang.Integer getNormTaskId() {
		return normTaskId;
	}
}



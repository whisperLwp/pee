package com.hongedu.pems.pems.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.jmx.export.annotation.ManagedAttribute;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_task_project 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_norm_task_project", pk="normTaskProjectId")
public class NormTaskProject extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核任务项目id*/
	private java.lang.Integer normTaskProjectId;
	/**项目id*/
	private java.lang.Integer projectId;
	/**考核任务id*/
	private java.lang.Integer normTaskId;
	/**考核员工id*/
	private java.lang.Integer employeeId;
	/**项目权重*/
	private Integer weight;
	/**状态：1初始、2赋权重*/
	private java.lang.Integer status;
	
	private String workContent;
	
	private Integer type;
	
	private Integer weight2;
	
	private String referFlag;
	
	public String getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(String referFlag) {
		this.referFlag = referFlag;
	}

	public Integer getWeight2() {
		return weight2;
	}

	public void setWeight2(Integer weight2) {
		this.weight2 = weight2;
	}

	@ManyToOne(joinKey="project_id")
	private Project project;
	
	@ManyToOne(joinKey="norm_task_id")
	private NormTask normTask;
	
	@ManyToOne(joinKey="employee_id")
	private Employee employee;
	
	@Instant
	private List<TaskProjectEmployee> eachEmp;
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public List<TaskProjectEmployee> getEachEmp() {
		return eachEmp;
	}

	public void setEachEmp(List<TaskProjectEmployee> eachEmp) {
		this.eachEmp = eachEmp;
	}

	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public NormTask getNormTask() {
		return normTask;
	}

	public void setNormTask(NormTask normTask) {
		this.normTask = normTask;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public void setNormTaskProjectId(java.lang.Integer value) {
		this.normTaskProjectId = value;
	}
	
	public java.lang.Integer getNormTaskProjectId() {
		return normTaskProjectId;
	}
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return projectId;
	}
	public void setNormTaskId(java.lang.Integer value) {
		this.normTaskId = value;
	}
	
	public java.lang.Integer getNormTaskId() {
		return normTaskId;
	}
	public void setEmployeeId(java.lang.Integer value) {
		this.employeeId = value;
	}
	
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}
	public void setWeight(Integer value) {
		this.weight = value;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
}



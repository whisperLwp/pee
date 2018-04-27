package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_task_employee_detail 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_norm_task_employee_detail", pk="normTaskTmployeeDetailId")
public class NormTaskEmployeeDetail extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**员工考核任务明细id*/
	private java.lang.Integer normTaskTmployeeDetailId;
	/**员工id*/
	private java.lang.Integer employeeId;
	/**考核任务id*/
	private java.lang.Integer normTaskId;
	/**人力资源评分*/
	private java.lang.Float hrScore;
	/**岗位自评分*/
	private java.lang.Float deptSelfScore;
	/**岗位上级评分*/
	private java.lang.Float deptLevelScore;
	/**项目自评分*/
	private java.lang.Float projectSelfScore;
	/**项目上级评分*/
	private java.lang.Float projectLevelScore;
	/**项目互评分*/
	private java.lang.Float projectEachScore;
	/**总分*/
	private java.lang.Float score;
	
	private String passFlag;
	
	//评分状态
	@Instant
	private Integer hrF;
	@Instant
	private Integer gsF;
	@Instant
	private Integer glF;
	@Instant
	private Integer xsF;
	@Instant
	private Integer xeF;
	@Instant
	private Integer xlF;
	
	
	public String getPassFlag() {
		return passFlag;
	}

	public void setPassFlag(String passFlag) {
		this.passFlag = passFlag;
	}

	public Integer getHrF() {
		return hrF;
	}

	public void setHrF(Integer hrF) {
		this.hrF = hrF;
	}

	public Integer getGsF() {
		return gsF;
	}

	public void setGsF(Integer gsF) {
		this.gsF = gsF;
	}

	public Integer getGlF() {
		return glF;
	}

	public void setGlF(Integer glF) {
		this.glF = glF;
	}

	public Integer getXsF() {
		return xsF;
	}

	public void setXsF(Integer xsF) {
		this.xsF = xsF;
	}

	public Integer getXeF() {
		return xeF;
	}

	public void setXeF(Integer xeF) {
		this.xeF = xeF;
	}

	public Integer getXlF() {
		return xlF;
	}

	public void setXlF(Integer xlF) {
		this.xlF = xlF;
	}

	@ManyToOne(joinKey="employee_id")
	private Employee employee;
	
	@ManyToOne(joinKey="norm_task_id")
	private NormTask normTask;
	
	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public NormTask getNormTask() {
		return normTask;
	}

	public void setNormTask(NormTask normTask) {
		this.normTask = normTask;
	}

	public void setNormTaskTmployeeDetailId(java.lang.Integer value) {
		this.normTaskTmployeeDetailId = value;
	}
	
	public java.lang.Integer getNormTaskTmployeeDetailId() {
		return normTaskTmployeeDetailId;
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
	public void setHrScore(java.lang.Float value) {
		this.hrScore = value;
	}
	
	public java.lang.Float getHrScore() {
		return hrScore;
	}
	public void setDeptSelfScore(java.lang.Float value) {
		this.deptSelfScore = value;
	}
	
	public java.lang.Float getDeptSelfScore() {
		return deptSelfScore;
	}
	public void setDeptLevelScore(java.lang.Float value) {
		this.deptLevelScore = value;
	}
	
	public java.lang.Float getDeptLevelScore() {
		return deptLevelScore;
	}
	public void setProjectSelfScore(java.lang.Float value) {
		this.projectSelfScore = value;
	}
	
	public java.lang.Float getProjectSelfScore() {
		return projectSelfScore;
	}
	public void setProjectLevelScore(java.lang.Float value) {
		this.projectLevelScore = value;
	}
	
	public java.lang.Float getProjectLevelScore() {
		return projectLevelScore;
	}
	public void setProjectEachScore(java.lang.Float value) {
		this.projectEachScore = value;
	}
	
	public java.lang.Float getProjectEachScore() {
		return projectEachScore;
	}
	public void setScore(java.lang.Float value) {
		this.score = value;
	}
	
	public java.lang.Float getScore() {
		return score;
	}
}



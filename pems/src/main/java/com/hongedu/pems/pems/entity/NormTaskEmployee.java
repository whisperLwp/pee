package com.hongedu.pems.pems.entity;

import java.io.Serializable;
import java.util.List;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_task_employee 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_norm_task_employee", pk="normTaskEmployeeId")
public class NormTaskEmployee extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核任务员工id*/
	private java.lang.Integer normTaskEmployeeId;
	/**考核任务id*/
	private java.lang.Integer normTaskId;
	/**员工id*/
	private java.lang.Integer employeeId;
	/**考核类型id（冗余）*/
	private java.lang.Integer normCategoryId;
	/**考核指标id*/
	private java.lang.Integer normId;
	/**打分人id（人力资源、互评人、上级）*/
	private java.lang.Integer scoreEmployeeId;
	/**项目id*/
	private java.lang.Integer projectId;
	/**得分*/
	private Integer score;
	/**指标分类：1人力、2岗位、3项目*/
	private java.lang.Integer normType;
	/**打分分类：1自评、2互评、3上级*/
	private java.lang.Integer scoreType;
	
	private Integer workContentId;
	
	public Integer getWorkContentId() {
		return workContentId;
	}

	public void setWorkContentId(Integer workContentId) {
		this.workContentId = workContentId;
	}

	@ManyToOne(joinKey="norm_task_id")
	private NormTask normTask;
	
	@ManyToOne(joinKey="norm_id")
	private Norm norm;
	
	@ManyToOne(joinKey="score_employee_id")
	private Employee employee;
	
	@ManyToOne(joinKey="norm_category_id")
	private NormCategory normCategory;

	@Instant
	private List<NormDetail> normDetailList;
	
	public NormCategory getNormCategory() {
		return normCategory;
	}

	public void setNormCategory(NormCategory normCategory) {
		this.normCategory = normCategory;
	}
	
	public List<NormDetail> getNormDetailList() {
		return normDetailList;
	}

	public void setNormDetailList(List<NormDetail> normDetailList) {
		this.normDetailList = normDetailList;
	}

	public Norm getNorm() {
		return norm;
	}

	public void setNorm(Norm norm) {
		this.norm = norm;
	}

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

	public void setNormTaskEmployeeId(java.lang.Integer value) {
		this.normTaskEmployeeId = value;
	}
	
	public java.lang.Integer getNormTaskEmployeeId() {
		return normTaskEmployeeId;
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
	public void setNormCategoryId(java.lang.Integer value) {
		this.normCategoryId = value;
	}
	
	public java.lang.Integer getNormCategoryId() {
		return normCategoryId;
	}
	public void setNormId(java.lang.Integer value) {
		this.normId = value;
	}
	
	public java.lang.Integer getNormId() {
		return normId;
	}
	public void setScoreEmployeeId(java.lang.Integer value) {
		this.scoreEmployeeId = value;
	}
	
	public java.lang.Integer getScoreEmployeeId() {
		return scoreEmployeeId;
	}
	public void setProjectId(java.lang.Integer value) {
		this.projectId = value;
	}
	
	public java.lang.Integer getProjectId() {
		return projectId;
	}
	public void setScore(Integer value) {
		this.score = value;
	}
	
	public Integer getScore() {
		return score;
	}

	public java.lang.Integer getNormType() {
		return normType;
	}

	public void setNormType(java.lang.Integer normType) {
		this.normType = normType;
	}

	public java.lang.Integer getScoreType() {
		return scoreType;
	}

	public void setScoreType(java.lang.Integer scoreType) {
		this.scoreType = scoreType;
	}
	
}



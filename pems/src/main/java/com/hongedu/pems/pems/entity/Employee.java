package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_sys_employee 表对应实体
 * 2018/01/31 02:37:13
 */
/**
 * @author song
 *
 */
@Table(name="el_sys_employee", pk="employeeId")
public class Employee extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**员工id*/
	private java.lang.Integer employeeId;
	/**员工登录名*/
	private java.lang.String employeeName;
	/**真实姓名*/
	private java.lang.String realName;
	/**密码*/
	private java.lang.String password;
	/**身份证号*/
	private java.lang.String idCard;
	/**所属部门编号*/
	private java.lang.String deptId;
	/**性别：1男0女*/
	private java.lang.Integer sexFlag;
	/**生日*/
	private java.util.Date birthDate;
	/**参加工作时间*/
	private java.util.Date workDate;
	/**电话*/
	private java.lang.String phoneNum;
	/**邮箱*/
	private java.lang.String mailNum;
	/**直接上级员工id*/
	private java.lang.Integer levelEmployeeId;
	/**用户角色 1.管理员 2.部门负责人 3.普通员工 4.无需绩效考评人员**/
	private java.lang.Integer roleId;
	/**是否需要参见考评 1 需要  0 不需要*/
	private java.lang.String peFlag;
	
	private java.lang.Integer stationId;
	
	@Instant
	private java.lang.String levelEmployeeName;
	
	@Instant
	private java.lang.String deptName;
	
	public java.lang.Integer getStationId() {
		return stationId;
	}

	public void setStationId(java.lang.Integer stationId) {
		this.stationId = stationId;
	}

	public java.lang.String getDeptName() {
		return deptName;
	}

	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName;
	}

	public java.lang.String getPeFlag() {
		return peFlag;
	}

	public void setPeFlag(java.lang.String peFlag) {
		this.peFlag = peFlag;
	}

	public java.lang.String getLevelEmployeeName() {
		return levelEmployeeName;
	}

	public void setLevelEmployeeName(java.lang.String levelEmployeeName) {
		this.levelEmployeeName = levelEmployeeName;
	}

	//链表
	@ManyToOne(joinKey = "dept_id")
	private Dept dept;
	
	@ManyToOne(joinKey = "station_id")
	private Station station;
	
	public Station getStation() {
		return station;
	}

	public void setStation(Station station) {
		this.station = station;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public void setEmployeeId(java.lang.Integer value) {
		this.employeeId = value;
	}
	
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeName(java.lang.String value) {
		this.employeeName = value;
	}
	
	public java.lang.String getEmployeeName() {
		return employeeName;
	}
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	
	public java.lang.String getRealName() {
		return realName;
	}
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return password;
	}
	public void setIdCard(java.lang.String value) {
		this.idCard = value;
	}
	
	public java.lang.String getIdCard() {
		return idCard;
	}
	
	
	public java.lang.String getDeptId() {
		return deptId;
	}

	public void setDeptId(java.lang.String deptId) {
		this.deptId = deptId;
	}

	public java.lang.Integer getSexFlag() {
		return sexFlag;
	}

	public void setSexFlag(java.lang.Integer sexFlag) {
		this.sexFlag = sexFlag;
	}

	public void setBirthDate(java.util.Date value) {
		this.birthDate = value;
	}
	
	public java.util.Date getBirthDate() {
		return birthDate;
	}
	public void setWorkDate(java.util.Date value) {
		this.workDate = value;
	}
	
	public java.util.Date getWorkDate() {
		return workDate;
	}
	public void setPhoneNum(java.lang.String value) {
		this.phoneNum = value;
	}
	
	public java.lang.String getPhoneNum() {
		return phoneNum;
	}
	public void setMailNum(java.lang.String value) {
		this.mailNum = value;
	}
	
	public java.lang.String getMailNum() {
		return mailNum;
	}
	
	public void setLevelEmployeeId(java.lang.Integer value) {
		this.levelEmployeeId = value;
	}
	
	public java.lang.Integer getLevelEmployeeId() {
		return levelEmployeeId;
	}

	public java.lang.Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(java.lang.Integer roleId) {
		this.roleId = roleId;
	}
	
}



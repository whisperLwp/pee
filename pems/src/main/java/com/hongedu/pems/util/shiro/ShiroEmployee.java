package com.hongedu.pems.util.shiro;

import java.io.Serializable;
import java.util.Date;

public class ShiroEmployee implements Serializable{
	
	 	/**
	 * 
	 */
	private static final long serialVersionUID = 1671794686192935567L;

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
	/**用户角色 1.管理员 2.部门负责人 3.普通员工 4.无需绩效考评人员*/
	private java.lang.Integer roleId;
	
	public ShiroEmployee(Integer employeeId, String employeeName, String realName, String password, String idCard,
			String deptId, Integer sexFlag, Date birthDate, Date workDate, String phoneNum, String mailNum,
			Integer levelEmployeeId, Integer roleId) {
		super();
		this.employeeId = employeeId;
		this.employeeName = employeeName;
		this.realName = realName;
		this.password = password;
		this.idCard = idCard;
		this.deptId = deptId;
		this.sexFlag = sexFlag;
		this.birthDate = birthDate;
		this.workDate = workDate;
		this.phoneNum = phoneNum;
		this.mailNum = mailNum;
		this.levelEmployeeId = levelEmployeeId;
		this.roleId = roleId;
	}
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(java.lang.Integer employeeId) {
		this.employeeId = employeeId;
	}
	public java.lang.String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(java.lang.String employeeName) {
		this.employeeName = employeeName;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	public java.lang.String getPassword() {
		return password;
	}
	public void setPassword(java.lang.String password) {
		this.password = password;
	}
	public java.lang.String getIdCard() {
		return idCard;
	}
	public void setIdCard(java.lang.String idCard) {
		this.idCard = idCard;
	}
	public java.lang.Integer getSexFlag() {
		return sexFlag;
	}
	public void setSexFlag(java.lang.Integer sexFlag) {
		this.sexFlag = sexFlag;
	}
	public java.util.Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(java.util.Date birthDate) {
		this.birthDate = birthDate;
	}
	public java.util.Date getWorkDate() {
		return workDate;
	}
	public void setWorkDate(java.util.Date workDate) {
		this.workDate = workDate;
	}
	public java.lang.String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(java.lang.String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public java.lang.String getMailNum() {
		return mailNum;
	}
	public void setMailNum(java.lang.String mailNum) {
		this.mailNum = mailNum;
	}
	public java.lang.Integer getLevelEmployeeId() {
		return levelEmployeeId;
	}
	public void setLevelEmployeeId(java.lang.Integer levelEmployeeId) {
		this.levelEmployeeId = levelEmployeeId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public java.lang.Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(java.lang.Integer roleId) {
		this.roleId = roleId;
	}
	public java.lang.String getDeptId() {
		return deptId;
	}
	public void setDeptId(java.lang.String deptId) {
		this.deptId = deptId;
	}
	    
}

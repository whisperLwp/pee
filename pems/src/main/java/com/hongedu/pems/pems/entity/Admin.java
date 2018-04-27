package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_sys_admin 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_sys_admin", pk="adminId")
public class Admin extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**用户id*/
	private java.lang.Integer adminId;
	/**登录账号*/
	private java.lang.String adminName;
	/**管理员姓名*/
	private java.lang.String realName;
	/**电话号码*/
	private java.lang.String phoneNum;
	/**公司邮箱*/
	private java.lang.String mailNum;
	/**密码*/
	private java.lang.String password;
	
	public void setAdminId(java.lang.Integer value) {
		this.adminId = value;
	}
	
	public java.lang.Integer getAdminId() {
		return adminId;
	}
	public void setAdminName(java.lang.String value) {
		this.adminName = value;
	}
	
	public java.lang.String getAdminName() {
		return adminName;
	}
	public void setRealName(java.lang.String value) {
		this.realName = value;
	}
	
	public java.lang.String getRealName() {
		return realName;
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
	public void setPassword(java.lang.String value) {
		this.password = value;
	}
	
	public java.lang.String getPassword() {
		return password;
	}
}



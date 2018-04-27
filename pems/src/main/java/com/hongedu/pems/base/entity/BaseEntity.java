package com.hongedu.pems.base.entity;

import java.io.Serializable;

/**
 * ORM对象基类
 * 使用方法：拥有相同字段的ORM需要继承BaseEntity，否则不需要集成BaseEntity
 * @author zyb
 *
 */
public class BaseEntity implements Serializable{
	
	/***/
	private static final long serialVersionUID = 1L;
	
	private java.util.Date createTime;
	private java.lang.String creater;
	private java.util.Date updateTime;
	private java.lang.String updater;
	private java.lang.String delFlag;
	
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.String getCreater() {
		return creater;
	}
	public void setCreater(java.lang.String creater) {
		this.creater = creater;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	public java.lang.String getUpdater() {
		return updater;
	}
	public void setUpdater(java.lang.String updater) {
		this.updater = updater;
	}
	public java.lang.String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(java.lang.String delFlag) {
		this.delFlag = delFlag;
	}
	
}

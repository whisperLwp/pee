package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_station 表对应实体
 * 2018/04/17 04:16:35
 */
@Table(name="el_bp_station", pk="stationId")
public class Station extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/***/
	private java.lang.Integer stationId;
	/**岗位名称*/
	private java.lang.String stationName;
	/**岗位描述*/
	private java.lang.String remark;
	
	private java.lang.String referFlag;
	
	public java.lang.String getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(java.lang.String referFlag) {
		this.referFlag = referFlag;
	}

	public void setStationId(java.lang.Integer value) {
		this.stationId = value;
	}
	
	public java.lang.Integer getStationId() {
		return stationId;
	}
	public void setStationName(java.lang.String value) {
		this.stationName = value;
	}
	
	public java.lang.String getStationName() {
		return stationName;
	}
	public void setRemark(java.lang.String value) {
		this.remark = value;
	}
	
	public java.lang.String getRemark() {
		return remark;
	}
}



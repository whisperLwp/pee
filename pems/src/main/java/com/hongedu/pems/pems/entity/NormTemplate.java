package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_template 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_norm_template", pk="normTempId")
public class NormTemplate extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**指标模板id*/
	private java.lang.Integer normTempId;
	/**指标模板名称*/
	private java.lang.String normTempName;
	/**创建时间*/
	private java.util.Date creatTime;
	/**是否被引用 ： 1 .被引用  2 .未被引用*/
	private java.lang.String referFlag;
	
	public java.lang.String getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(java.lang.String referFlag) {
		this.referFlag = referFlag;
	}

	public void setNormTempId(java.lang.Integer value) {
		this.normTempId = value;
	}
	
	public java.lang.Integer getNormTempId() {
		return normTempId;
	}
	public void setNormTempName(java.lang.String value) {
		this.normTempName = value;
	}
	
	public java.lang.String getNormTempName() {
		return normTempName;
	}
	public void setCreatTime(java.util.Date value) {
		this.creatTime = value;
	}
	
	public java.util.Date getCreatTime() {
		return creatTime;
	}
}



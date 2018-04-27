package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_detail 表对应实体
 * 2018/01/31 02:37:11
 */
@Table(name="el_bp_norm_detail", pk="normDetailId")
public class NormDetail extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核指标明细id*/
	private java.lang.Integer normDetailId;
	/**排序*/
	private Integer orderNum;
	/**描述*/
	private java.lang.String desrc;
	/**分值*/
	private Integer score;
	/**最大分值*/
	private Integer maxScore;
	/*考核指标Id*/
	private Integer normId; 
	
	public Integer getNormId() {
		return normId;
	}

	public void setNormId(Integer normId) {
		this.normId = normId;
	}

	public void setNormDetailId(java.lang.Integer value) {
		this.normDetailId = value;
	}
	
	public java.lang.Integer getNormDetailId() {
		return normDetailId;
	}
	public void setOrderNum(Integer value) {
		this.orderNum = value;
	}
	
	public Integer getOrderNum() {
		return orderNum;
	}
	public void setDesrc(java.lang.String value) {
		this.desrc = value;
	}
	
	public java.lang.String getDesrc() {
		return desrc;
	}
	public void setScore(Integer value) {
		this.score = value;
	}
	
	public Integer getScore() {
		return score;
	}
	public void setMaxScore(Integer value) {
		this.maxScore = value;
	}
	
	public Integer getMaxScore() {
		return maxScore;
	}
}



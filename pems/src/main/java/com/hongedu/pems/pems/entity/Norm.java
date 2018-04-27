package com.hongedu.pems.pems.entity;

import java.io.Serializable;
import java.util.List;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Instant;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm 表对应实体
 * 2018/01/31 02:37:11
 */
@Table(name="el_bp_norm", pk="normId")
public class Norm extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核指标id*/
	private java.lang.Integer normId;
	/**考核指标名称*/
	private java.lang.String normName;
	/**考核指标类型id*/
	private java.lang.Integer normCategoryId;
	/**权重*/
	private Integer weight;
	
	@ManyToOne(joinKey="norm_category_id")
	private NormCategory category;
	
	@Instant
	private List<NormDetail> normDetailList;
	
	public List<NormDetail> getNormDetailList() {
		return normDetailList;
	}

	public void setNormDetailList(List<NormDetail> normDetailList) {
		this.normDetailList = normDetailList;
	}

	public NormCategory getCategory() {
		return category;
	}

	public void setCategory(NormCategory category) {
		this.category = category;
	}

	public void setNormId(java.lang.Integer value) {
		this.normId = value;
	}
	
	public java.lang.Integer getNormId() {
		return normId;
	}
	public void setNormName(java.lang.String value) {
		this.normName = value;
	}
	
	public java.lang.String getNormName() {
		return normName;
	}
	public void setNormCategoryId(java.lang.Integer value) {
		this.normCategoryId = value;
	}
	
	public java.lang.Integer getNormCategoryId() {
		return normCategoryId;
	}
	public void setWeight(Integer value) {
		this.weight = value;
	}
	
	public Integer getWeight() {
		return weight;
	}
}



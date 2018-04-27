package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_category 表对应实体
 * 2018/01/31 02:37:11
 */
@Table(name="el_bp_norm_category", pk="normCategoryId")
public class NormCategory extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核指标类型id*/
	private java.lang.Integer normCategoryId;
	/**考核指标类型名称*/
	private java.lang.String normCategoryName;
	/**人力资源指标权重*/
	private Integer weight;
	/**自评指标权重*/
	private Integer selfWeight;
	/**上级指标权重*/
	private Integer levelWeight;
	/**互评指标权重*/
	private Integer eachWeight;
	/**指标模板id*/
	private java.lang.Integer normTemplateId;
	/**指标分类：1人力、2岗位、3项目*/
	private java.lang.Integer type;
	
	public void setNormCategoryId(java.lang.Integer value) {
		this.normCategoryId = value;
	}
	
	public java.lang.Integer getNormCategoryId() {
		return normCategoryId;
	}
	public void setNormCategoryName(java.lang.String value) {
		this.normCategoryName = value;
	}
	
	public java.lang.String getNormCategoryName() {
		return normCategoryName;
	}
	public void setWeight(Integer value) {
		this.weight = value;
	}
	
	public Integer getWeight() {
		return weight;
	}
	public void setSelfWeight(Integer value) {
		this.selfWeight = value;
	}
	
	public Integer getSelfWeight() {
		return selfWeight;
	}
	public void setLevelWeight(Integer value) {
		this.levelWeight = value;
	}
	
	public Integer getLevelWeight() {
		return levelWeight;
	}
	public void setEachWeight(Integer value) {
		this.eachWeight = value;
	}
	
	public Integer getEachWeight() {
		return eachWeight;
	}
	public void setNormTemplateId(java.lang.Integer value) {
		this.normTemplateId = value;
	}
	
	public java.lang.Integer getNormTemplateId() {
		return normTemplateId;
	}

	public java.lang.Integer getType() {
		return type;
	}

	public void setType(java.lang.Integer type) {
		this.type = type;
	}
	
}



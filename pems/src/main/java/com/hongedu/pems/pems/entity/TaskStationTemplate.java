package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_task_station_template 表对应实体
 * 2018/04/18 09:30:38
 */
@Table(name="el_bp_task_station_template", pk="taskStationTemplateId")
public class TaskStationTemplate extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/***/
	private java.lang.Integer taskStationTemplateId;
	/**考核任务Id*/
	private java.lang.Integer normTaskId;
	/***/
	private java.lang.Integer stationId;
	/***/
	private java.lang.Integer normTemplateId;
	
	public void setTaskStationTemplateId(java.lang.Integer value) {
		this.taskStationTemplateId = value;
	}
	
	public java.lang.Integer getTaskStationTemplateId() {
		return taskStationTemplateId;
	}
	public void setNormTaskId(java.lang.Integer value) {
		this.normTaskId = value;
	}
	
	public java.lang.Integer getNormTaskId() {
		return normTaskId;
	}
	public void setStationId(java.lang.Integer value) {
		this.stationId = value;
	}
	
	public java.lang.Integer getStationId() {
		return stationId;
	}
	public void setNormTemplateId(java.lang.Integer value) {
		this.normTemplateId = value;
	}
	
	public java.lang.Integer getNormTemplateId() {
		return normTemplateId;
	}
}



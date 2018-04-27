package com.hongedu.pems.pems.entity;

import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;

import com.hongedu.pems.base.entity.BaseEntity;
import com.hongedu.pems.base.dao.annotation.ManyToOne;
import com.hongedu.pems.base.dao.annotation.Table;
/**
 * @author  
 * el_bp_norm_task 表对应实体
 * 2018/01/31 02:37:12
 */
@Table(name="el_bp_norm_task", pk="normTaskId")
public class NormTask extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**考核任务id*/
	private java.lang.Integer normTaskId;
	/**考核任务名称*/
	private java.lang.String normTaskName;
	/**状态：1进行中、2结束、3、归档中、4归档*/
	private java.lang.Integer status;
	/**开始时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date startTime;
	/**结束时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date endTime;
	/**评分截至时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date scoreTime;
	/**归档时间*/
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private java.util.Date finishTime;
	
	private java.lang.String referFlag;
	
	public java.lang.String getReferFlag() {
		return referFlag;
	}

	public void setReferFlag(java.lang.String referFlag) {
		this.referFlag = referFlag;
	}

	public void setNormTaskId(java.lang.Integer value) {
		this.normTaskId = value;
	}
	
	public java.lang.Integer getNormTaskId() {
		return normTaskId;
	}
	public void setNormTaskName(java.lang.String value) {
		this.normTaskName = value;
	}
	
	public java.lang.String getNormTaskName() {
		return normTaskName;
	}
	
	public java.lang.Integer getStatus() {
		return status;
	}

	public void setStatus(java.lang.Integer status) {
		this.status = status;
	}

	public void setStartTime(java.util.Date value) {
		this.startTime = value;
	}
	
	public java.util.Date getStartTime() {
		return startTime;
	}
	public void setEndTime(java.util.Date value) {
		this.endTime = value;
	}
	
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setScoreTime(java.util.Date value) {
		this.scoreTime = value;
	}
	
	public java.util.Date getScoreTime() {
		return scoreTime;
	}
	public void setFinishTime(java.util.Date value) {
		this.finishTime = value;
	}
	
	public java.util.Date getFinishTime() {
		return finishTime;
	}
}



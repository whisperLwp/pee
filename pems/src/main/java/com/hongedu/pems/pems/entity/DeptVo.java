package com.hongedu.pems.pems.entity;

import java.util.List;

public class DeptVo {
	
	/**
	 * 部门信息
	 */
	private Integer deptId;
	
	private String deptName;
	
	private String deptCode;
	
	private String levelId;
	
	private List<DeptVo> list;

	

	public List<DeptVo> getList() {
		return list;
	}

	public void setList(List<DeptVo> list) {
		this.list = list;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}


}

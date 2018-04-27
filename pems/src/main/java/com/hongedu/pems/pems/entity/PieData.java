package com.hongedu.pems.pems.entity;

public class PieData {
	
	private String name;
	
	private float num;
	
	private String txt;

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getNum() {
		return num;
	}

	public void setNum(float num) {
		this.num = num;
	}

	public PieData(String name, float num,String txt) {
		super();
		this.name = name;
		this.num = num;
		this.txt = txt;
	}

	public PieData() {
		super();
	}
	

}

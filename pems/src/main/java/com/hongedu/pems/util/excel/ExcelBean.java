package com.hongedu.pems.util.excel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExcelBean {
	/**失败记录数*/
	private int errCount;
	/**下载路径*/
	private String downloadPath;
	/**函数返回值，0成功，1失败*/
	private String result;
	/**总记录数*/
	private int count;
	/**整体失败原因*/
	private String errMsg;
	
	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public int getErrCount() {
		return errCount;
	}

	public void setErrCount(int errCount) {
		this.errCount = errCount;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	
	

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	public static void main(String[] args) {
		String[] pram = new String[]{"1","2"};
		String[] errPram = new String[pram.length+1];
		List<String> _arr = new ArrayList<String>(Arrays.asList(pram));
		Arrays.asList(pram);
		_arr.add("3");
		_arr.toArray(errPram);
		System.out.println(_arr);
		
	}
}

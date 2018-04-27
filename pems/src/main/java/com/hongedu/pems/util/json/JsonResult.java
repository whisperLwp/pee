package com.hongedu.pems.util.json;

/**
 * json请求统一返回对象
 * @author zyb
 *
 */
public class JsonResult {
	
	/**成功*/
	public static final String SUCCESS_CODE = "000000";
	/**失败*/
	public static final String FAILE_CODE = "999999";
	
	
	private String errorCode;
	
	private String errorMessage;
	
	private Object data;

	public JsonResult(String errorCode, String errorMessage, Object data){
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.data = data;
	}
	
	
	public String getErrorCode() {
		return errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public Object getData() {
		return data;
	}
	
	
}

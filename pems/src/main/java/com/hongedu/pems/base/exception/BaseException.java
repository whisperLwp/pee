package com.hongedu.pems.base.exception;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;



/**
 * 自定义异常类
 * @author zyb
 *
 */
public class BaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public BaseException(Throwable cause) {
		//this("", "", cause, true);
		excuteException(cause);
	}
	public static void excuteException(Throwable cause){
		try {
			
//			String remortIp = getRemortIP(req);
//			System.out.println(remortIp);
			
			ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
			cause.printStackTrace(new java.io.PrintWriter(buf, true));
			String  expMessage = buf.toString();
			System.out.println("***s");
			System.out.println(expMessage);
			System.out.println("***e");
			buf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static String getRemortIP(HttpServletRequest request) {  
		  if (request.getHeader("x-forwarded-for") == null) {  
		   return request.getRemoteAddr();  
		  }  
		  return request.getHeader("x-forwarded-for");  
		 } 
}

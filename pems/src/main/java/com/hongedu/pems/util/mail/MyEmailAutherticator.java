package com.hongedu.pems.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class MyEmailAutherticator extends Authenticator {

	// 用户名（登录邮箱）
	private String username;
	// 密码
	private String password;

	@Override
	// 用作登录校验，以确保对该邮箱有发送邮件的权利
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

	public MyEmailAutherticator() {
		super();
	}

	// 初始化邮箱和密码
	public MyEmailAutherticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

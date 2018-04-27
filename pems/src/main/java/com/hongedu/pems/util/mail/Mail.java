package com.hongedu.pems.util.mail;

/**
 * 邮件类
 */
public class Mail {

	private String username;// 登录邮件发送服务器的用户名

	private String password;// 登录邮件发送服务器的密码

	private String host;// 服务器ip

	private String mail_head_name;// 邮件头文件名

	private String mail_head_value;// 邮件头文件值

	private String mail_to;// 收件人邮箱地址

	private String mail_copy;// 抄送人邮箱地址

	private String mail_from;// 发件人邮箱地址

	private String mail_subject;// 邮件的标题

	private String mail_content;// 邮件的内容

	private String[] attachFileNames;// 附件名称

	private String personalName;// 邮件的名称

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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMail_head_name() {
		return mail_head_name;
	}

	public void setMail_head_name(String mail_head_name) {
		this.mail_head_name = mail_head_name;
	}

	public String getMail_head_value() {
		return mail_head_value;
	}

	public void setMail_head_value(String mail_head_value) {
		this.mail_head_value = mail_head_value;
	}

	public String getMail_to() {
		return mail_to;
	}

	public void setMail_to(String mail_to) {
		this.mail_to = mail_to;
	}

	public String getMail_copy() {
		return mail_copy;
	}

	public void setMail_copy(String mail_copy) {
		this.mail_copy = mail_copy;
	}

	public String getMail_from() {
		return mail_from;
	}

	public void setMail_from(String mail_from) {
		this.mail_from = mail_from;
	}

	public String getMail_subject() {
		return mail_subject;
	}

	public void setMail_subject(String mail_subject) {
		this.mail_subject = mail_subject;
	}

	public String getMail_content() {
		return mail_content;
	}

	public void setMail_content(String mail_content) {
		this.mail_content = mail_content;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(String[] attachFileNames) {
		this.attachFileNames = attachFileNames;
	}

	public String getPersonalName() {
		return personalName;
	}

	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
}

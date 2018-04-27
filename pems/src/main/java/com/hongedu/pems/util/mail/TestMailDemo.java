package com.hongedu.pems.util.mail;


public class TestMailDemo {

	public static void main(String[] args) {
		Mail mail = new Mail();
		mail.setUsername("saicheng168@163.com");
		mail.setPassword("song0911");
		mail.setHost("smtp.163.com");
		mail.setMail_head_name("this is head of this mail");
		mail.setMail_head_value("this is head of this mail");
		mail.setMail_to("657721722@qq.com");// 填写收件人邮箱
//		mail.setMail_copy("");// 填写抄送人邮箱
		mail.setMail_from("saicheng168@163.com");
		mail.setMail_subject("测试");
		StringBuffer demo = new StringBuffer();
		demo.append(
				"<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">")
				.append("<html>").append("<head>")
				.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">")
				.append("<title>测试邮件</title>").append("<style type=\"text/css\">")
				.append(".test{font-family:\"Microsoft Yahei\";font-size: 18px;color: red;}").append("</style>")
				.append("</head>").append("<body>").append("<span class=\"test\">大家好，这里是测试Demo</span>")
				.append("</body>").append("</html>");
		mail.setMail_content(demo.toString());
		mail.setPersonalName("我的邮件");
		MailUtil.sendMail(mail);
	}
}

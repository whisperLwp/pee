package com.hongedu.pems.util.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailManager {
	
	/*private static String smtp_host = "smtp.163.com"; 
	private static String username = "saicheng168@163.com"; //发件人
	private static String password = "song0911"; 
	private static String from = "saicheng168@163.com"; // 使用当前账户
*/	
	public static String activeUrl = "";
	
	@Value("#{prop['mail.smtpHost']}")
	public String smtp_host;
	@Value("#{prop['mail.username']}")
	public String username;
	@Value("#{prop['mail.password']}")
	public String password;
	@Value("#{prop['mail.from']}")
	public String from;
	@Value("#{prop['exception.switch']}")
	public String exception_switch;
	
	
	/**
	 * 发送邮件
	 * @param subject 邮件主题
	 * @param content 邮件内容
	 * @param to 收件人
	 */
	public void sendMail(String subject, String content, String to) {
		if(StringUtils.equals(exception_switch, "OFF")){
			return;
		}
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtp_host);
		props.setProperty("mail.transport.protocol", "smtp");
		props.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(props);
		Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setContent(content, "text/html;charset=utf-8");
			Transport transport = session.getTransport();
			transport.connect(smtp_host, username, password);
			transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送失败...");
		}
	}
	public class MailJob {
		    
			/*String to="sac@song.com";
			MailUtils.sendMail("订单信息", fasongneirong, to);*/
		}
	
}

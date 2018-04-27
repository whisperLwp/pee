package com.hongedu.pems.util.mail;

import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 邮件工具类
 */
public class MailUtil {
	
	//私有化构造器，工具类无需创建对象
	private MailUtil() {
		
	}
	
	public static void sendMail(Mail mail) {
		try {
			// 发送邮件的props文件
			Properties props = new Properties();
			// 初始化props
			props.setProperty("mail.transport.protocol", "smtp");
			props.setProperty("mail.smtp.host", mail.getHost());
			props.setProperty("mail.smtp.auth", "true");
			// 设置SSL连接、邮件环境
			final String smtpPort = "465";
			props.setProperty("mail.smtp.port", smtpPort);
			props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", smtpPort);
			// 进行邮件服务用户认证
			Authenticator auth = new MyEmailAutherticator(mail.getUsername(), mail.getPassword());
			// 创建session,和邮件服务器进行通讯
			Session session = Session.getDefaultInstance(props, auth);
			session.setDebug(true);
			// 创建mime类型邮件
			MimeMessage message = new MimeMessage(session);
			// 整封邮件的MINE消息体
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含附件内容的
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			// 设置HTML内容(或纯文本内容)
			messageBodyPart.setContent(mail.getMail_content(), "text/html; charset=utf-8");
			mainPart.addBodyPart(messageBodyPart);
			// 存在附件
			String[] filePaths = mail.getAttachFileNames();
			if (filePaths != null && filePaths.length > 0) {
				for (String filePath : filePaths) {
					messageBodyPart = new MimeBodyPart();
					File file = new File(filePath);
					if (file.exists()) {// 附件存在磁盘中
						FileDataSource fds = new FileDataSource(file);// 得到数据源
						messageBodyPart.setDataHandler(new DataHandler(fds));// 得到附件本身并至入BodyPart
						messageBodyPart.setFileName(file.getName());// 得到文件名同样至入BodyPart
						mainPart.addBodyPart(messageBodyPart);
					}
				}
			}
			// 将MimeMultipart对象设置为邮件内容
			message.setContent(mainPart);
			// 设置主题
			message.setSubject(mail.getMail_subject());
			// 设置邮件内容
			// message.setText(mail.getMail_content());
			// 设置邮件标题
			message.setHeader(mail.getMail_head_name(), mail.getMail_head_value());
			message.setSentDate(new Date());// 设置邮件发送时期
			Address address = new InternetAddress(mail.getMail_from(), mail.getPersonalName());
			// 设置邮件发送者的地址
			message.setFrom(address);
			// 设置收件人邮箱
			message.setRecipients(RecipientType.TO, mail.getMail_to());
			if(mail.getMail_copy()!= null && !"".equals(mail.getMail_copy())) {
				// 设置抄送人邮箱
				message.setRecipients(RecipientType.CC, mail.getMail_copy());
			}
			// 保存设置
			message.saveChanges();
			// 根据 Session 获取邮件传输对象
			Transport transport = session.getTransport();
			// 获取链接
			transport.connect(mail.getUsername(), mail.getPassword());
			// 发送邮件
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

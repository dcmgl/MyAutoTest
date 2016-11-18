package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;

import base.TestLogger;


public class SendMail {
	private Properties pro = new Properties();
	private EmailAttachment attach = null;
	private MultiPartEmail mail = null;
	private static Logger log = TestLogger.getLogger(SendMail.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SendMail mail;
		try {
			mail = new SendMail("c:\\system.properties");
			mail.sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	public SendMail (String proPath) throws Exception{
		try {
			pro.load(new FileInputStream(proPath));
			if(pro.getProperty("mail.isSend").equals("true")){
				mail = new MultiPartEmail();
				attach = new EmailAttachment();
			}else{
				log.info("邮件处于关闭状态，不会发送");
				throw new Exception("邮件处于关闭状态，不会发送");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
	}
	
	public void sendMail(){
		this.initMail();
		try {
			mail.send();
			new File(attach.getPath()).delete();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			log.error("邮件发送失败",e);
		}
	}
	
	private void initMail(){
		try {
			mail.setHostName(pro.getProperty("mail.smtp.host").trim());
			mail.setSmtpPort(Integer.parseInt(pro.getProperty("mail.smtp.port").trim()));
			mail.setFrom(pro.getProperty("mail.from.user").trim());
			mail.setAuthentication(pro.getProperty("mail.username").trim(),pro.getProperty("mail.password").trim());
			mail.setSubject(pro.getProperty("mail.subject").trim());
			mail.setMsg(pro.getProperty("mail.msg").trim());
			if(pro.getProperty("mail.to.users").trim().contains(";")){
				String toUsers[]=pro.getProperty("mail.to.users").split(";");
				for(String toUser : toUsers){
					mail.addTo(toUser.trim());
				}
			}else{
				mail.addTo(pro.getProperty("mail.to.users"));
			}
			File file = new File(pro.getProperty("mail.attach.path").trim());
			if(file.isDirectory()){
				ZipUtil.zip(file.getAbsolutePath(), file.getParent(), "mailAttach.zip");
				attach.setPath(file.getParent()+file.separator+"mailAttach.zip");
			}else{
				attach.setPath(pro.getProperty("mail.attach.path").trim());
			}
			attach.setDisposition(EmailAttachment.ATTACHMENT);
			mail.attach(attach);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("邮件初始化失败",e);
			return;
		}
		
	}
	
	

}

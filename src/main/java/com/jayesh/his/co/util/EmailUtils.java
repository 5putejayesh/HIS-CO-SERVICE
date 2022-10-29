package com.jayesh.his.co.util;

import java.io.FileInputStream;

import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils {

	private Logger logger=LoggerFactory.getLogger(EmailUtils.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to,String subject,String body,FileInputStream fis) {
		boolean isMailSent=false;
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper messageHelper=new MimeMessageHelper(mimeMessage,true);
			
			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(body,true);
			messageHelper.addAttachment("PlanDtls.pdf", new ByteArrayResource(IOUtils.toByteArray(fis)));
			
			mailSender.send(mimeMessage);
			
			isMailSent=true;
		}
		catch (Exception e) {
			logger.error("Exception Occurred",e);
		}
		return isMailSent;
	}
}

package com.jwt.user.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Configuration
public class EmailUtil {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendEmail(String email,String subject, String text) throws MessagingException {
		MimeMessage mimeMessage =javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage);
		mimeMessageHelper.setTo(email);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(text);
		
		javaMailSender.send(mimeMessage);
		
		
	}
}
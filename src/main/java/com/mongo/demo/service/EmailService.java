package com.mongo.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.mongo.demo.document.Product;
import com.mongo.utility.Config;

@Component
public class EmailService {

	@Autowired
	public JavaMailSender emailSender;

	@Value("#{'${alert.user.mail}'.split(',')}")
	private List<String> emailIds;

	@Value(value = "${alert.user.subject}")
	private String subject;

	@Value(value = "${alert.user.text}")
	private String text;

	public void sendAlertMail(Product product) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailIds.toArray(new String[emailIds.size()]));
		message.setSubject(subject);
		message.setText(String.format(text, product.getName(),
				Config.format(product.getQtyAblBack(), Config.QTY_FORMATTER) + " " + product.getUnit().getName(),
				Config.format(product.getAlertBack(),Config.QTY_FORMATTER) + " " + product.getUnit().getName()));
		emailSender.send(message);

	}

}

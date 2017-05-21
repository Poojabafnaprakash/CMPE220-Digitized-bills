package com.cmpe220.ocr;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationEmail {

	public static void emailRecommendTrigger(String name,
			String monthlyExpen, String email) {
		final String username = "vsaikruthi92@gmail.com";
		final String password = "Sairam@03";
		String to = email;
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});
		// Session session = Session.getInstance(props, null);

		try {

			Message message = new MimeMessage(session);
			InternetAddress me = new InternetAddress("DigitizedBill.com");
			try {
				me.setPersonal("Research Exchange Program");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setFrom(me);
			//for (int i = 0; i < to.length; i++) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			//}
			message.setSubject("Mothly Expenditure");
			message.setText("Dear " + name + ",\n"
					+ "\nYour monthly expenditure is "+monthlyExpen+"." 
					+ "\n\nRegards,\n" + "DigitizedBill Team");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
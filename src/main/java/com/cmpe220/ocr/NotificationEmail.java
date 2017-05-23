package com.cmpe220.ocr;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class NotificationEmail {
	
	public static HashMap<Integer,String> monthsMap = new HashMap<Integer,String>();

	public static void emailRecommendTrigger(String name,
			String monthlyExpen, String email, int month) {
		monthsMap.put(1, "January");
		monthsMap.put(2, "February");
		monthsMap.put(3, "March");
		monthsMap.put(4, "April");
		monthsMap.put(5, "May");
		monthsMap.put(6, "June");
		monthsMap.put(7, "July");
		monthsMap.put(8, "August");
		monthsMap.put(9, "September");
		monthsMap.put(10, "October");
		monthsMap.put(11, "November");
		monthsMap.put(12, "December");
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
				me.setPersonal("Track monthly expenditure.");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			message.setFrom(me);
			//for (int i = 0; i < to.length; i++) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(to));
			//}
			message.setSubject("Mothly Expenditure");
			message.setText("Dear " + name + ",\n\n\n"
					+ "\nYour monthly expenditure for the month of "+monthsMap.get(month)+" is "+monthlyExpen+"." 
					+ "\n\nRegards,\n" + "DigitizedBill Team");

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}

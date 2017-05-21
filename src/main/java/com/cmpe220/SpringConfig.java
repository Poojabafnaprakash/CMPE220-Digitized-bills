//package com.cmpe220;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import com.cmpe220.ocr.NotificationEmail;
//
//
//@Configuration
//@EnableScheduling
//public class SpringConfig {
//	
//	@Scheduled(cron = "0 0 10 15 * ?")
//	public void scheduleTaskUsingCronExpression() {
//
//			Integer expen = 0;
//			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//			try {
//				java.util.Date date1 = format.parse("2017/04/01");
//				java.util.Date date2 = format.parse("2017/04/30");
//				expen = splitService.findMonthlyExpen(user,
//						new java.sql.Date(date1.getTime()),
//						new java.sql.Date(date2.getTime()));
//				if (expen != null) {
//					NotificationEmail.emailRecommendTrigger(user.getFirstName(),
//							expen.toString(), user.getEmailId());
//				}
//
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//
//		
//	}
//
//}

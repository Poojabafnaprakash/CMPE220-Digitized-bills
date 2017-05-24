package com.cmpe220;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.cmpe220.model.User;
import com.cmpe220.ocr.NotificationEmail;
import com.cmpe220.service.SplitService;
import com.cmpe220.service.UserService;

@Configuration
@EnableScheduling
public class SpringConfig {

	@Autowired
	private SplitService splitService;

	@Autowired
	private UserService userService;

	List<User> user;

	@Scheduled(cron = "0 45 8 ? * *")
	public void scheduleTaskUsingCronExpression() {
		user = new ArrayList<User>();
		Double expen = 0.0;
		try {
			java.util.Date date= new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH);
			for (User u : user) {
				expen = splitService.findMonthlyExpen(u,month);
				if (expen != null) {
					NotificationEmail.emailRecommendTrigger(u.getFirstName(),
							expen.toString(), u.getEmailId(), month);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

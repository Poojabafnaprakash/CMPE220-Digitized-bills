package com.cmpe220.controller;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cmpe220.model.TwitterData;
import com.cmpe220.model.User;
import com.cmpe220.service.TwitterDataService;
import com.cmpe220.service.UserService;

@Controller
@RequestMapping(value = "/connect")
@SessionAttributes("user")
public class ConnectController extends
		org.springframework.social.connect.web.ConnectController {
	private Twitter twitter;
	private ConnectionRepository connectionRepository;

	@Autowired
	private UserService userServiceObj;

	@Autowired
	private OcrImageToTextConverterController controller2;

	@Autowired
	private TwitterDataService twitterDataServiceObj;
	private User currentUser;

	@Inject
	public ConnectController(ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository, Twitter twitter) {
		super(connectionFactoryLocator, connectionRepository);
		this.connectionRepository = connectionRepository;
		this.twitter = twitter;
	}

	@Override
	protected String connectedView(String providerId) {
		twitter = connectionRepository.getPrimaryConnection(Twitter.class)
				.getApi();

		long twitterId = twitter.userOperations().getProfileId();
		String twitterName = twitter.userOperations().getUserProfile()
				.getName();
		TwitterData twitterUser = twitterDataServiceObj
				.getTwitterDataUser(twitterId);
		if (null != twitterUser) {
			currentUser = userServiceObj.getUser(twitterUser.getUserId());
		} else {
			currentUser = new User();
			currentUser.setFirstName(twitterName);
			// currentUser.setEmailId(twitter.userOperations().getUserProfile());
			currentUser = userServiceObj.addUser(currentUser);
			TwitterData twitterUserNew = new TwitterData(twitterId,
					twitterName, currentUser.getId());
			twitterDataServiceObj.addTwitterDataUser(twitterUserNew);
		}

		return controller2.redirectToDashboard(currentUser);
	}

	@RequestMapping(value = "/currentUser", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public User getCurrentUser() {
		twitter = connectionRepository.getPrimaryConnection(Twitter.class)
				.getApi();
		TwitterData twitterUser = new TwitterData();

		long twitterId = twitter.userOperations().getProfileId();
		twitterUser = twitterDataServiceObj.getTwitterDataUser(twitterId);
		User currentUser = userServiceObj.getUser(twitterUser.getUserId());

		return currentUser;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json")
	public String logout() {
		twitter = null;
		return "hello";
	}

}
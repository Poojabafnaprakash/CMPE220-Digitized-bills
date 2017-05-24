package com.cmpe220.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cmpe220.model.Bill;
import com.cmpe220.model.Friend;
import com.cmpe220.model.Groups;
import com.cmpe220.model.Items;
import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;
import com.cmpe220.model.UserGroups;
import com.cmpe220.object.AllBills;
import com.cmpe220.object.JsonRequestWrapper;
import com.cmpe220.object.MonthlyExpen;
import com.cmpe220.ocr.NotificationEmail;
import com.cmpe220.ocr.OcrImageToTextConverterService;
import com.cmpe220.service.BillService;
import com.cmpe220.service.GetFriendsService;
import com.cmpe220.service.GroupService;
import com.cmpe220.service.ItemsService;
import com.cmpe220.service.SplitService;
import com.cmpe220.service.UserGroupService;
import com.cmpe220.service.UserService;

@RestController
@SessionAttributes("user")
public class OcrImageToTextConverterController {

	@Autowired
	private OcrImageToTextConverterService getTextFromReceiptService;

	@Autowired
	private BillService billservice;

	@Autowired
	private ItemsService itemsService;

	@Autowired
	private GetFriendsService getFriendsService;

	@Autowired
	private UserService userService;

	@Autowired
	private SplitService splitService;

	@Autowired
	private GroupService groupService;

	@Autowired
	private UserGroupService userGroupService;

	public Bill bill;
	public Groups groups;
	public List<Bill> bills;
	public List<Friend> friendsList;
	//public List<AllBills> allBills;
	public HashMap<AllBills, AllBills> allBills;

	public Items items;
	public SplitReceipt splitReceipt;
	public List<SplitReceipt> splitReceipts;
	public List<Items> itemsList;

	public HashMap<Integer, String> userMap = new HashMap<Integer, String>();
	public User user;
	public List<User> names = new ArrayList<>();
	JsonRequestWrapper obj = new JsonRequestWrapper();
	
	public List<User> notFriends = new ArrayList<>();

	@CrossOrigin(origins = "*")
	@PostMapping("/getReceiptDetails")
	public String getReceiptDetails(
			@RequestParam(value = "filePath", required = true, defaultValue = "None") String filePath,
			Model model) {
		model.addAttribute("name", filePath);
		model.addAttribute("object", obj);
		return "bill";
	}

	public String redirectToDashboard(User user) {
		this.user = new User();
		this.user = user;
		return "redirect:/#!/dashboard";
	}

	@RequestMapping("/receiptInfo")
	public JsonRequestWrapper getReceiptInfo() {
		obj = getTextFromReceiptService.getReceiptDetails(user.getFirstName());
		obj.setUserID(user.getId());
		return obj;

	}

	@PostMapping("/saveBillDetails")
	public JsonRequestWrapper saveBill(@RequestBody JsonRequestWrapper obj) {
		this.obj = obj;
		bill = new Bill();
		items = new Items();
		itemsList = new ArrayList<>();
		
		//bill.setBillPath("C:\\Users\\vsaik\\Documents\\GitHub\\CMPE220-Digitized-bills\\src\\main\\resources\\receiptImage\\bill2.png");
		bill.setBillPath("/Users/poojaprakashchand/Documents/Eclipse/CMPE220-Digitized-bills/src/main/resources/receiptImage/bill2.png");
		bill.setTotal(obj.getTotal());
		bill.setTax(obj.getTax());
		bill.setUserId(user);
		bill.setBillName(obj.getBillName());
		bill.setTotOrItem(obj.getFlag());
		System.out.println(obj.getTotal());
		bill = billservice.addBill(bill);
		System.out.println(obj.getItems().size());
		for (int i = 0; i < obj.getItems().size(); i++) {
			obj.getItems().get(i).setBillId(bill);
			items = itemsService.addItems(obj.getItems().get(i));
			itemsList.add(items);
		}

		if (obj.getFlag().equals("T")) {
			obj.setItems(null);
		}
		obj.setUserID(user.getId());

		return obj;
	}

	@RequestMapping("/getUserFriends")
	public List<User> getFriends() {
		try{
			friendsList = new ArrayList<>();
			friendsList = getFriendsService.findFriends(user);
			List<Integer> friendIds = new ArrayList<>();
			for (Friend i : friendsList) {
				friendIds.add(i.getFriendId().getId());
			}

			names = userService.findUsers(friendIds);
			for (User u : names) {
				userMap.put(u.getId(), u.getFirstName());
			}
			userMap.put(user.getId(), user.getFirstName());
		} catch(Exception ex){
			names = new ArrayList<User>();
		}
		
		return names;
	}
	

	@RequestMapping("/user")
	public User getUser() {
		return user;
	}
	
//	@RequestMapping("/notFriends")
//	public List<User> getNotFriends() {
//		try{
//			List<Friend> friends = getFriendsService.findNotFriends(user);
//			List<Integer> friendIds = new ArrayList<>();
//			for (Friend i : friends) {
//				friendIds.add(i.getFriendId().getId());
//			}
//			notFriends = userService.findUsers(friendIds);
//		} catch(Exception ex){
//			notFriends = new ArrayList<User>();
//		}
//		
//		return notFriends;
//	}
	
	
	@RequestMapping("/notFriends")
	 public List<User> getNotFriends() {
	  try {
	   List<Friend> friends = getFriendsService.findNotFriends(user);
	   if (friends != null) {
	    List<Integer> friendIds = new ArrayList<>();
	    for (Friend i : friends) {
	     friendIds.add(i.getFriendId().getId());
	    }
	    notFriends = userService.findUsers(friendIds);
	   }else{
	    notFriends = userService.getAllUsers();
	    for(int i=0;i<notFriends.size();i++){
	     if(notFriends.get(i).getId()==user.getId()){
	      notFriends.remove(i);
	      break;
	     }
	    }
	   }
	  } catch (Exception ex) {
	   notFriends = userService.getAllUsers();
	   for(int i=0;i<notFriends.size();i++){
	    if(notFriends.get(i).getId()==user.getId()){
	     notFriends.remove(i);
	     break;
	    }
	   }
	  }

	  return notFriends;
	 }
	
	@PostMapping("/addNotFriends")
	public void addFriends(@RequestBody List<User> friends) {
		Friend frnd = null;
		for(User f:friends){
			frnd = new Friend();
			frnd.setFriendId(f);
			frnd.setUserId(user);
			getFriendsService.addFriend(frnd);
		}
	}

	@PostMapping("/addFriends")
	public void addFriends(@RequestBody JsonRequestWrapper object) {
		int splitBy = 0;
		if (object.getFlag().equals("T")) {
			splitBy = object.getSplitIds().size();
			int index = splitBy - 1;
			Double amount = (object.getTotal() / splitBy);
			while (index >= 0) {
				splitReceipt = new SplitReceipt();
				splitReceipt.setAmount(amount);
				splitReceipt.setBillId(bill);
				splitReceipt.setDateCreated(new java.sql.Date(new Date()
						.getTime()));
				splitReceipt.setPaidBy(user);
				splitReceipt.setUserId(object.getSplitIds().get(index));
				splitReceipt = splitService.addItems(splitReceipt);
				index--;
			}

		} else {
			int index = object.getItems().size() - 1;
			while (index >= 0) {
				if (object.getItems().get(index).getSplitIds() != null) {
					int splitIndex = object.getItems().get(index).getSplitIds()
							.size() - 1;
					Double amount = (object.getItems().get(index)
							.getItemPrice() / (splitIndex + 1));
					while (splitIndex >= 0) {
						splitReceipt = new SplitReceipt();
						splitReceipt.setAmount(amount);
						splitReceipt.setBillId(bill);
						splitReceipt.setDateCreated(new java.sql.Date(
								new Date().getTime()));
						splitReceipt.setPaidBy(user);
						splitReceipt.setUserId(object.getItems().get(index)
								.getSplitIds().get(splitIndex));
						splitReceipt.setItemId(object.getItems().get(index));
						splitReceipt = splitService.addItems(splitReceipt);
						splitIndex--;
					}
				}

				index--;
			}
		}
		this.obj = object;
		
	}

	@RequestMapping("/getEditableBills")
	public List<Bill> getBills() {
		try{
			bills = new ArrayList<Bill>();		
			bills = billservice.findBills(user);
			for(int i=0; i<bills.size(); i++){
				if(bills.get(i).getTotOrItem().equals("I")){
					bills.get(i).setTotOrItem("Item");
				}
				else{
					bills.get(i).setTotOrItem("Total");
				}
			}
		} catch (Exception ex){
			bills = new ArrayList<Bill>();
		}
		
		return bills;
		
	}

	@RequestMapping("/createGroup")
	public Groups createGroup() {
		this.groups = new Groups();		
		this.groups.setUserIds(names);
		return this.groups;
	}
	
	
	@RequestMapping("/sendNotification")
	public void sendNotification() {
		Double expen = 0.0;
		
		try {
			java.util.Date date= new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int month = cal.get(Calendar.MONTH) + 1;
			System.out.println(month);
			expen = splitService.findMonthlyExpen(user,month);
			try{
				if (expen != null) {
					NotificationEmail.emailRecommendTrigger(user.getFirstName(),
							expen.toString(), user.getEmailId(),month);
				}
			} catch(Exception ex){
				NotificationEmail.emailRecommendTrigger(user.getFirstName(),
						"2.66", user.getEmailId(),month);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/saveFriendsGroup")
	public void saveFriendsGroup(@RequestBody Groups groups) {
		UserGroups us = null;
		this.groups = groupService.createGroup(groups);
		for (User u : groups.getUserIds()) {
			if (!(u.getChecked().equals(null)) && u.getChecked().equals("Yes")) {
				us = new UserGroups();
				us.setUserId(u);
				us.setGroups(this.groups);
				userGroupService.saveFriends(us);
			}
		}
		 
	}

	@RequestMapping("/viewGroups")
	@ResponseBody
	public List<Groups> viewGroup() {
		List<Groups> groups = new ArrayList<>();
		groups = groupService.getAllGroups();
		return groups;
	}

	

	@RequestMapping("/monthlyExpenditure")
	public List<MonthlyExpen> getmonthlyExpenditure() {
		
		List<MonthlyExpen> expens = new ArrayList<MonthlyExpen>();
		MonthlyExpen expen = new MonthlyExpen();
		String[] expenArray = splitService.findMonthlyExpenYear(user, "2017");
		System.out.println(expenArray.length);
		System.out.println(expenArray);
		for (int i = 0; i < expenArray.length; i++) {
			expen = new MonthlyExpen();
			String[] arr = expenArray[i].split(",");
			expen.setExpen(arr[0]);
			expen.setMonth(arr[1]);
			expens.add(expen);
		}

		return expens;
	}

	@RequestMapping("/youOwe")
	public List<String> getYouOwe() {
		List<String> youOwe = new ArrayList<String>();
		StringBuffer sb;
		int count =0;
		Object amount =null;
		List<Map<Double, User>> sp = splitService.findOweDetails(user);
		for (int i = 0; i < sp.size(); i++) {
			sb = new StringBuffer();
			sb.append("You owe $");
			count = 0;
			for (Map.Entry<Double, User> entry : sp.get(i).entrySet()) {
				count++;
				if (count == 1) {
					amount = entry.getValue();
				} else if (count == 2) {
					sb.append(userMap.get(entry.getValue().getId()));
					sb.append(" ");
					sb.append(amount + ".");
					youOwe.add(sb.toString());
				}
			}
		}

		return youOwe;
	}

	@RequestMapping("/youAreOwed")
	public List<String> getYouAreOwed() {
		List<String> youAreOwed = new ArrayList<String>();
		List<Map<Double, User>> sp = splitService.findOwedDetails(user);
		System.out.println(sp.size());
		StringBuffer sb;
		int count = 0;
		for (int i = 0; i < sp.size(); i++) {
			sb = new StringBuffer();
			sb.append("You are Owed $");
			count = 0;
			for (Map.Entry<Double, User> entry : sp.get(i).entrySet()) {
				count++;
				if (count == 1) {
					sb.append(entry.getValue());
					sb.append(" by ");
				} else if (count == 2) {
					sb.append(userMap.get(entry.getValue().getId()));
					youAreOwed.add(sb.toString());
				}
			}
		}
		return youAreOwed;
	}
	

	@RequestMapping("/totalYouOwe")
	public Double getTotalYouOwe() {
		
		Double amount = splitService.findTotalYouOwe(user);
		
		return Double.parseDouble(String.format("%.2f", amount));
	}
	

	@RequestMapping("/totalYouAreOwed")
	public Double getTotalYouAreOwed() {
		
		Double amount = splitService.findTotalYouAreOwed(user);
		
		return Double.parseDouble(String.format("%.2f", amount));
	}
}

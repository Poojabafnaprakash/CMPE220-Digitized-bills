package com.cmpe220.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.cmpe220.object.JsonRequestWrapper;
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

	public Items items;
	public SplitReceipt splitReceipt;
	public List<SplitReceipt> splitReceipts;
	public List<Items> itemsList;

	public User user;
	public List<User> names = new ArrayList<>();
	JsonRequestWrapper obj = new JsonRequestWrapper();

	@CrossOrigin(origins = "*")
	@PostMapping("/getReceiptDetails")
	public String getReceiptDetails(
			@RequestParam(value = "filePath", required = true, defaultValue = "None") String filePath,
			Model model) {
		model.addAttribute("name", filePath);
		// obj.setTotal("73.33");
		model.addAttribute("object", obj);
		return "bill";
	}

	public String redirectToDashboard(User user) {
		this.user = new User();
		System.out.println("Hi");
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
		bill.setBillPath("C:\\Users\\vsaik\\Documents\\GitHub\\CMPE220-Digitized-bills\\src\\main\\resources\\receiptImage\\bill2.png");
		// bill.setBillPath("/Users/poojaprakashchand/Documents/Eclipse/CMPE220-Digitized-bills/src/main/resources/receiptImage/bill2.png");
		bill.setTotal(obj.getTotal());
		bill.setTax(obj.getTax());
		bill.setUserId(user);
		bill.setBillName(obj.getBillName());
		bill.setTotOrItem(obj.getFlag());
		bill = billservice.addBill(bill);
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
		friendsList = new ArrayList<>();
		friendsList = getFriendsService.findFriends(user);
		List<Integer> friendIds = new ArrayList<>();
		for (Friend i : friendsList) {
			friendIds.add(i.getFriendId().getId());
		}

		names = userService.findUsers(friendIds);
		return names;
	}

	@PostMapping("/addFriends")
	public String addFriends(@RequestBody JsonRequestWrapper object) {
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
				splitReceipt.setPaidBy(object.getPaidBy());
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
						splitReceipt.setPaidBy(object.getItems().get(index)
								.getPaidBy());
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
		return "redirect:/#!/dashboard";
	}

	@RequestMapping("/getEditableBills")
	public List<Bill> getBills() {
		bills = new ArrayList<>();
		bills = billservice.findBills(user);
		return bills;
	}

	@RequestMapping("/createGroup")
	public Groups createGroup() {
		this.groups = new Groups();
		this.groups.setGroupName("Enter Group Name");
		this.groups.setUserIds(names);
		return this.groups;
	}

	@RequestMapping("/saveFriendsGroup")
	public Groups saveFriendsGroup(@RequestBody Groups groups) {
		System.out.println("In save group method");
		UserGroups us = null;
		for(User g: groups.getUserIds()){
			System.out.println("Checked is "+g.getChecked());
		}
		this.groups = groupService.createGroup(groups);
		for (User u : groups.getUserIds()) {
			us = new UserGroups();
			us.setUserId(u);
			us.setGroups(this.groups);
			userGroupService.saveFriends(us);
		}
		return this.groups;
	}

	@RequestMapping("/viewGroups")
	@ResponseBody
	public List<Groups> viewGroup() {
		List<Groups> groups = new ArrayList<>();
		groups = groupService.getAllGroups();
		return groups;
	}

	@RequestMapping("/getItemsFromReceipt")
	public List<Items> getItemsFromReceipt() {
		List<Items> itm = getTextFromReceiptService.getItemsFromReceipt();
		return itm;
	}

	@RequestMapping("/getTotalFromReceipt")
	public Double getTotalFromReceipt() {
		Double total = null;
		total = getTextFromReceiptService.getTotalFromReceipt();
		return total;
	}

	@RequestMapping("/getTaxFromReceipt")
	public Double getTaxFromReceipt() {
		Double total = null;
		total = getTextFromReceiptService.getTaxFromReceipt();
		return total;
	}

	@RequestMapping("/monthlyExpenditure")
	public Integer getmonthlyExpenditure() {
		Integer expen = 0;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		try {
			java.util.Date date1 = format.parse("2017/04/01");
			java.util.Date date2 = format.parse("2017/04/30");
			expen = splitService.findMonthlyExpen(user,
					new java.sql.Date(date1.getTime()),
					new java.sql.Date(date2.getTime()));
			if (expen != null) {
				NotificationEmail.emailRecommendTrigger(user.getFirstName(),
						expen.toString(), user.getEmailId());
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return expen;
	}
}

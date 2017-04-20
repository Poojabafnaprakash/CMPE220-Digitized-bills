package com.cmpe220.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hsqldb.lib.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcProperties.View;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cmpe220.model.Bill;
import com.cmpe220.model.Friend;
import com.cmpe220.model.Items;
import com.cmpe220.model.User;
import com.cmpe220.object.Item;
import com.cmpe220.object.JsonRequestWrapper;
import com.cmpe220.ocr.OcrImageToTextConverterService;
import com.cmpe220.service.BillService;
import com.cmpe220.service.GetFriendsService;
import com.cmpe220.service.ItemsService;

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

	public Bill bill;

	public List<Bill> bills;
	public List<Friend> friend ;

	public Items items;
	public Set<Items> itemsList;

	public User user;

	JsonRequestWrapper obj = new JsonRequestWrapper();

	@CrossOrigin(origins = "*")
	@PostMapping("/getReceiptDetails")
	public String getReceiptDetails(
			@RequestParam(value = "filePath", required = true, defaultValue = "None") String filePath,
			Model model) {
		model.addAttribute("name", filePath);
		obj = getTextFromReceiptService.getReceiptDetails(filePath);
		System.out.println("Total is" + obj.getTotal());
		obj.setTotal("73.33");
		model.addAttribute("object", obj);
		return "bill";
	}

	
	public String redirectToDashboard(User user) {
		System.out.println("User id is "+user.getUserId());
		friend = new ArrayList<>();
		this.user = new User();
		this.user = user;
		
		return "redirect:/#!/dashboard";
	}
	
	@RequestMapping("/getUserFriends")
	public List<Friend> getFriends(){
		friend = new ArrayList<>();
		friend = getFriendsService.findFriends(user);
		return friend;
	}

	@PostMapping("/getEditableBills")
	public List<Bill> getBills(Model model) {

		bills = new ArrayList<>();
		bills = billservice.findBills(user);
		System.out.println("Bills list size is " + bills.size());
		return bills;
	}

	@PostMapping("/getItemDetails")
	public String getTotorItem(@RequestParam("split") String splitI,
			@ModelAttribute JsonRequestWrapper object, Model model) {
		bill = new Bill();
		items = new Items();
		bill.setBillPath("C://Users//vsaik//Desktop//bill.png");
		bill.setTotal(Double.parseDouble(obj.getTotal()));
		bill.setTax(Double.parseDouble(obj.getTax()));
		bill.setUserId(user);
		bill.setItems(null);
		bill = billservice.addBill(bill);
		if (splitI.equals("Total")) {
			object.setItems(null);
		} else {
			for (Item i : obj.getItems()) {
				items = new Items();
				items.setBillId(bill);
				items.setItemDescription(i.getItemDescription());
				items.setItemPrice(i.getItemAmt());
				items = itemsService.addItems(items);
			}

		}
		model.addAttribute("object", object);
		return "split";
	}

	@PostMapping("/addFriends")
	public String addFriends(@ModelAttribute JsonRequestWrapper object,
			Model model) {
		object.setTotal("200");
		model.addAttribute("object", object);
		return "split";
	}

	@RequestMapping("/getItemsFromReceipt")
	public List<Item> getItemsFromReceipt() {
		List<Item> itm = getTextFromReceiptService.getItemsFromReceipt();
		return itm;
	}

	@RequestMapping("/getTotalFromReceipt")
	public String getTotalFromReceipt() {
		String total = null;
		total = getTextFromReceiptService.getTotalFromReceipt();
		return total;
	}

	@RequestMapping("/getTaxFromReceipt")
	public String getTaxFromReceipt() {
		String total = null;
		total = getTextFromReceiptService.getTaxFromReceipt();
		return total;
	}
}

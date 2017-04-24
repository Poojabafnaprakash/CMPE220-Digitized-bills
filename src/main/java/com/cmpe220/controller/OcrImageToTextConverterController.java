package com.cmpe220.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.assertj.core.util.Sets;
import org.hsqldb.lib.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.cmpe220.model.Bill;
import com.cmpe220.model.Friend;
import com.cmpe220.model.Items;
import com.cmpe220.model.SplitReceipt;
import com.cmpe220.model.User;
import com.cmpe220.object.Item;
import com.cmpe220.object.JsonRequestWrapper;
import com.cmpe220.ocr.OcrImageToTextConverterService;
import com.cmpe220.repository.SplitRepository;
import com.cmpe220.service.BillService;
import com.cmpe220.service.GetFriendsService;
import com.cmpe220.service.ItemsService;
import com.cmpe220.service.SplitService;
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

	public Bill bill;

	public List<Bill> bills;
	public List<Friend> friendsList;

	public Items items;
	public SplitReceipt splitReceipt;
	public List<SplitReceipt> splitReceipts;
	public List<Items> itemsList;

	public User user;

	JsonRequestWrapper obj = new JsonRequestWrapper();

	@CrossOrigin(origins = "*")
	@PostMapping("/getReceiptDetails")
	public String getReceiptDetails(
			@RequestParam(value = "filePath", required = true, defaultValue = "None") String filePath,
			Model model) {
		model.addAttribute("name", filePath);
		obj.setTotal("73.33");
		model.addAttribute("object", obj);
		return "bill";
	}

	public String redirectToDashboard(User user) {
		System.out.println("User id is " + user.getUserId());
		this.user = new User();
		this.user = user;
		return "redirect:/#!/dashboard";
	}

	@RequestMapping("/receiptInfo")
	public JsonRequestWrapper getReceiptInfo() {
		obj = getTextFromReceiptService.getReceiptDetails();
		return obj;

	}
	
	@PostMapping("/saveBillDeatils")
	public String saveBill() {
		bill = new Bill();
		items = new Items();
		itemsList = new ArrayList<>();
		bill.setBillPath("C:\\Users\\vsaik\\Documents\\GitHub\\CMPE220-Digitized-bills\\src\\main\\resources\\receiptImage\\bill2.png");
		bill.setTotal(Double.parseDouble(obj.getTotal()));
		bill.setTax(Double.parseDouble(obj.getTax()));
		bill.setUserId(user);

		for (Item i : obj.getItems()) {
			items = new Items();
			items.setBillId(bill);
			items.setItemDescription(i.getItemDescription());
			items.setItemPrice(i.getItemAmt());
			items = itemsService.addItems(items);
			itemsList.add(items);
		}
		bill.setItems(Sets.newHashSet(itemsList));
		bill = billservice.addBill(bill);
		return null;
	}
	
	@PostMapping("/getItemDetails")
	public String getTotorItem(@RequestParam("split") String splitI,
			@ModelAttribute JsonRequestWrapper object, Model model) {

		if (splitI.equals("Total")) {
			object.setItems(null);
			object.setFlag("T");
			bill.setTotOrItem("T");
		} else {
			object.setFlag("I");
			bill.setTotOrItem("I");
		}
		bill = billservice.addBill(bill);//check if it is updating
		model.addAttribute("object", object);
		return "split";
	}

	@RequestMapping("/getUserFriends")
	public List<User> getFriends() {
		friendsList = new ArrayList<>();
		friendsList = getFriendsService.findFriends(user);
		List<Integer> friendIds = new ArrayList<>();
		for (Friend i : friendsList) {
			friendIds.add(i.getFriendId().getUserId());
		}
		List<User> names = new ArrayList<>();
		names = userService.findUsers(friendIds);
		return names;
	}
	
	@PostMapping("/addFriends")
	public String addFriends(@ModelAttribute JsonRequestWrapper object,
			Model model) {
		int splitBy = 0;
		if (object.getFlag().equals("T")) {
			splitBy = object.getSplitIds().size();
			int index = splitBy - 1;
			float amount = (float) (Double.parseDouble(object.getTotal()) / splitBy);
			while (index >= 0) {
				splitReceipt = new SplitReceipt();
				splitReceipt.setAmount(amount);
				splitReceipt.setBillId(bill);
				splitReceipt.setPaidBy(object.getPaidBy());
				splitReceipt.setUserId(object.getSplitIds().get(index));
				splitReceipt = splitService.addItems(splitReceipt);
				index--;
			}

		} else {
			int index = object.getItems().size() - 1;
			while (index >= 0) {
				int splitIndex = object.getItems().get(index).getSplitIds()
						.size() - 1;
				float amount = (float) (object.getItems().get(0).getItemAmt() / (splitIndex + 1));
				while (splitIndex >= 0) {
					splitReceipt = new SplitReceipt();
					splitReceipt.setAmount(amount);
					splitReceipt.setBillId(bill);
					splitReceipt.setPaidBy(object.getPaidBy());
					splitReceipt.setUserId(object.getItems().get(index)
							.getSplitIds().get(splitIndex));
					splitReceipt = splitService.addItems(splitReceipt);
					splitIndex--;
				}

				index--;
			}
		}

		model.addAttribute("object", object);
		return "split";
	}

	@PostMapping("/getEditableBills")
	public List<Bill> getBills(Model model) {
		bills = new ArrayList<>();
		bills = billservice.findBills(user);
		return bills;
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

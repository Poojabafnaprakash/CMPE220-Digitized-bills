package com.cmpe220.ocr;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cmpe220.model.Items;
import com.cmpe220.object.Item;
import com.cmpe220.object.JsonRequestWrapper;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@Service
public class OcrImageToTextConverterService {

	private final static String DEFAULT_TESSDATA_PATH = "C://Users//vsaik//Desktop//Tesseract-OCR";
	//private final static String DEFAULT_TESSDATA_PATH = "/usr/local/share";
	private final static String DEFAULT_PAGE_SEG_MODE = "3";
	private final static String DEFAULT_LANG = "eng";

	public String convertReceiptToText() {
		String textFromReceipt = null;
		//File imageFile = new File("C://Users//vsaik//Desktop//bill.png");
		//File imageFile = new File("./src/main/resources/receiptImage/bill1.png");
		File imageFile = new File("C:\\Users\\vsaik\\Documents\\GitHub\\CMPE220-Digitized-bills\\src\\main\\resources\\receiptImage\\bill2.png");
		Tesseract instance = new Tesseract();
		instance.setLanguage(DEFAULT_LANG);
		instance.setDatapath(DEFAULT_TESSDATA_PATH);
		instance.setPageSegMode(Integer.parseInt(DEFAULT_PAGE_SEG_MODE));
		try {
			textFromReceipt = instance.doOCR(imageFile);
//			textFromReceipt = "VDMEX 4PL‘S LEISURE SET 39.99"
//					+ System.lineSeparator() + "HERD SUPER EUNP UVER GRIP 4.99"
//					+ System.lineSeparator() + "HERO CLUB TR SHUTILEEHCK 21.99"
//					+ System.lineSeparator() + "SubTotal 66.97"
//					+ System.lineSeparator() + "Sales Tax  35"
//					+ System.lineSeparator() + "Total 73.33";
			//System.out.println(textFromReceipt);
			} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
		return textFromReceipt;
	}

	@ResponseBody
	public JsonRequestWrapper getReceiptDetails(String name) {
		OcrImageToTextConverterService serviceObj = new OcrImageToTextConverterService();
		JsonRequestWrapper obj = new JsonRequestWrapper();
		obj.setBillPath("filepath");
		//obj.setUserID(0);
        obj.setTax(serviceObj.getTaxFromReceipt());
		//obj.setTax(4.3);
		obj.setTotal(serviceObj.getTotalFromReceipt());
		//obj.setTotal(177.5);
		obj.setBillName(name+"-"+new Date().toString());
		obj.setItems(serviceObj.getItemsFromReceipt());
		return obj;
	}

	public List<Item> getItemDetails() {
		List<Item> items = null;

		// List<Item> items = Arrays.asList(
		// new Item("1", "biscuit", 10, Arrays.asList(
		// new SplitFriendsDetails(1, 5.0),
		// new SplitFriendsDetails(2, 5.0))), new Item("2",
		// "coke", 20,
		// // Arrays.asList(new SplitFriendsDetails(1,10.0),
		// // new SplitFriendsDetails(2,10.0))
		// Arrays.asList(null, null)), new Item("3", "pepsi",
		// 10, null));
		return items;
	}

	@ResponseBody
	public List<Items> getItemsFromReceipt() {
		String text = convertReceiptToText();
		Map<String, Double> rates = new HashMap<String, Double>();
		List<Items> items = new ArrayList<>();
		Items item = null;
		Scanner scanner = new Scanner(text);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			try {
				if (line.length() > 0) {
					if (Character.isDigit(line.charAt(line.length() - 1))) {
						item = new Items();
						rates = extract(line);
						for (Map.Entry<String, Double> o : rates.entrySet()) {
							if (!o.getKey().equals("N/A")) {
								item.setItemDescription(o.getKey());
								item.setItemPrice(o.getValue());
								items.add(item);
							}

						}

					} else if (Character
							.isDigit(line.charAt(line.length() - 2))) {
						item = new Items();
						rates = extract(line.substring(0, line.length() - 1));
						for (Map.Entry<String, Double> o : rates.entrySet()) {
							item.setItemDescription(null);
							item.setItemPrice(o.getValue());
						}
						items.add(item);
					} else if (Character
							.isDigit(line.charAt(line.length() - 3))) {
						item = new Items();
						rates = extract(line.substring(0, line.length() - 2));
						for (Map.Entry<String, Double> o : rates.entrySet()) {
							item.setItemDescription(null);
							item.setItemPrice(o.getValue());
						}
						items.add(item);
					}
				}
			} catch (Exception e) {

			}
		}
		scanner.close();
		return items;
	}

	public static Map<String, Double> extract(String string) {
		assert string != null;
		int i = string.lastIndexOf(' ');
		Map<String, Double> rates = new HashMap<>();
		if (i == -1)
			return null;
		String last = string.substring(i + 1);
		String rate = string.substring(0, i);
		if (rate.contains("total") || rate.contains("Total")
				|| rate.contains("TOTAL") || rate.contains("tax")
				|| rate.contains("Tax") || rate.contains("Due")) {
			rates.put("N/A", Double.parseDouble(last));
		} else {
			rates.put(rate, Double.parseDouble(last));
		}

		return rates;
	}

	@ResponseBody
	public Double getTotalFromReceipt() {
		String text = convertReceiptToText();
		String totalResult = null;
		List<String> tokens = new ArrayList<String>();
		tokens.add("Total Due");
		tokens.add("Due");
		
		//((Total Due | Due)(?=(:*[\s]*[$]*[0-9]+.[0-9]*)))+(:*[\s]*[$]*([0-9]+[\s]*.[\s]*[0-9]*))
		String patternString = "(("
				+ StringUtils.join(tokens, "|")
				+ ")(?=(:*[\\s]*[$]*[0-9]+.[0-9]*)))+(:*[\\s]*[$]*([0-9]+[\\s]*.[\\s]*[0-9]*))";
		
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			totalResult = matcher.group(5);
		}
		totalResult = totalResult.replaceAll("\\s+","");
		return Double.parseDouble(totalResult);
	}

	@ResponseBody
	public Double getTaxFromReceipt() {
		String text = convertReceiptToText();
		String tax = null;
		List<String> tokens = new ArrayList<String>();
		tokens.add("Tax");
		tokens.add("Card");
		String patternString = "(("
				+ StringUtils.join(tokens, "|")
				+ ")(?=(:*[\\s]*[$]*[0-9]+.[0-9]*)))+(:*[\\s]*[$]*([0-9]+.[0-9]*))";
		Pattern pattern = Pattern.compile(patternString);
		Matcher matcher = pattern.matcher(text);
		while (matcher.find()) {
			tax = matcher.group(5);
		}
		tax = tax.replaceAll("\\s+","");
		return Double.parseDouble(tax);
	}

}

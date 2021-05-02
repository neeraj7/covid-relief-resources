package com.covid.relief;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.Iterator;

import com.google.i18n.phonenumbers.PhoneNumberMatch;
import com.google.i18n.phonenumbers.PhoneNumberUtil;

public class TestMainClass {
	public static void main(String[] args) {
		
		String str = "35648292";
		
		String[] arr = str.split(",");
		for(String a : arr) {
			System.out.println(a);
		}
		System.out.println("Replacing from string which doesn't even exist in it.");
		str.replaceAll("7", "");
		
		System.out.println(str);
		
		String testPhone = "RT @RajarshitaS: Oxygen can and cylinder available.\\nContact :+91 98182 68256\\nQunatity: Concentrator of 7 liters \\nOxygen can available 12Lt";
		System.out.println("Replaced output -> " + testPhone.replaceAll("@RajarshitaS", "").replaceAll("RT", ""));
		
		
		System.out.println("Execution finished.");
		
		
		
		
	}
}

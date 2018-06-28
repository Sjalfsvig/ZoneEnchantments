package me.sjalfsvig.zoneenchantments.util;

public class RomanNumeral {

	public static String toRomanNumeral(String num) {
		String numeral = "";
		
		switch (num) {
		case "1":
			numeral = "I";
			break;
		case "2":
			numeral = "II";
			break;
		case "3":
			numeral = "III";
			break;
		case "4":
			numeral = "IV";
			break;
		case "5":
			numeral = "V";
			break;
		}
		
		return numeral;
	}
	
	public static int toInt(String romanNumeral) {
		int number = 0;
		
		switch (romanNumeral) {
		case "I":
			number = 1;
			break;
		case "II":
			number = 2;
			break;
		case "III":
			number = 3;
			break;
		case "IV":
			number = 4;
			break;
		case "V":
			number = 5;
			break;
		}
		
		return number;
	}
}

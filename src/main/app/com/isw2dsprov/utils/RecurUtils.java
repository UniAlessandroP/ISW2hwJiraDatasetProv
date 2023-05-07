package app.com.isw2dsprov.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class RecurUtils {

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }

	public static List<String> patternBasedSearch(String strpattern, String str) {
		Pattern pattern = Pattern.compile(strpattern);
		Matcher matcher = pattern.matcher(str);
		List<String> matches = null;
		while (matcher.find()) {
			if(matches == null) matches = new ArrayList<String>();
		    String match = matcher.group();
		    matches.add(match);	
		}
		return matches;
	}

	public static int getFirstNonDigitIdx(String substring) {
		int i = 0;
		for(;i < substring.length(); i++)
			if(!Character.isDigit(substring.charAt(i))) break;
		
		return i;
		
	}

	public static Date get8601DateFromText(String strdate) {
		return  getDateForFormat(strdate, "yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	}
	
	public static Date getDateForFormat(String strdate, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        try {
			return dateFormat.parse(strdate);
		} catch (ParseException e) {
			System.out.println("Cannot parse: " + strdate);
			e.getMessage();
			return null;
		}
	}
}

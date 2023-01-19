package com.blueteak.fbleads.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.blueteak.fbleads.constants.FbExtractConstants.FilterResponseConstants;

public class DateUtil {

	public static Date getDate(String date) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
		Date result = df.parse(date);
		return result;
	}

	public static Date genAfterDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date afterDate = null;
		try {
		    afterDate = formatter.parse(FilterResponseConstants.AFTER_DATE);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return afterDate;
	}

	public static Date genBeforeDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
		Date afterDate = null;
		try {
		    afterDate = formatter.parse(FilterResponseConstants.BEFORE_DATE);
		} catch (ParseException e) {
			System.out.println(e.getMessage());
		}
		return afterDate;
	}
}

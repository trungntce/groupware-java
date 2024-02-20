package kr.co.hs.common.util;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringFormatter {

	public static String toNumber(Object obj) {
		NumberFormat nf = NumberFormat.getInstance();
		return nf.format(obj);
	}
	
	public static String toDate(Date date, String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat(pattern);
		return sf.format(date);
	}
	
}

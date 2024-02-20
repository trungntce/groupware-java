package kr.co.hs.common.util;

import java.util.Locale;

import org.springframework.context.support.MessageSourceAccessor;

public class MessageUtil {
	/**
	 * MessageSourceAccessor
	 */
	private static MessageSourceAccessor messageSourceAccessor = null;

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		MessageUtil.messageSourceAccessor = messageSourceAccessor;
	}

	public static String getMessage(String key, Locale _locale) {
		return messageSourceAccessor.getMessage(key, _locale);
	}

	public static String getMessage(String key, Object[] objs, Locale _locale) {
		return messageSourceAccessor.getMessage(key, objs, _locale);
	}
	
	public static String getMessage(String key, String value, Locale _locale) {
		try {
			return messageSourceAccessor.getMessage(key, new String[]{value}, _locale);	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getMessage(String key, String value1, String value2, Locale _locale) {
		try {
			return messageSourceAccessor.getMessage(key, new String[]{value1, value2}, _locale);	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public static String getMessage(String key, String value1, String value2, String value3, Locale _locale) {
		try {
			return messageSourceAccessor.getMessage(key, new String[]{value1, value2, value3}, _locale);	
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}			
	}
}

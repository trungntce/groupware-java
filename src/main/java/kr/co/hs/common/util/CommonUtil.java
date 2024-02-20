package kr.co.hs.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

	public static String getIP(HttpServletRequest request) {
		String clientIp = request.getHeader("Proxy-Client-IP");
		if (clientIp == null) {
			clientIp = request.getHeader("WL-Proxy-Client-IP");
			if (clientIp == null) {
				clientIp = request.getHeader("X-Forwarded-For");
				if (clientIp == null) {
					clientIp = request.getRemoteAddr();
				}
			}
		}
		return clientIp;
	}

	public static Date getOldDay(int value, Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, value);
		return cal.getTime();
	}

	public static String StringReplace(String str) {
		str = str.replaceAll("(n)", "");
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "").replace(" ", "").toLowerCase();
		return str;
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static String getDomainByRequest(HttpServletRequest request) {
		String domain = "";
		try {
			domain = new URL( request.getRequestURL().toString() ).getHost().toString();
			domain = domain.toLowerCase();
			log.debug("domain================>"+domain);
			if(domain.startsWith("www")){
				String[] arrDomain = domain.split("\\.");
				domain = arrDomain[1]+"."+arrDomain[2];
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return domain;
	}

	public static boolean isOnlyLetterAndNumer(String str) {
		String pattern = "^[0-9a-zA-Z]*$";
		boolean returnBoolen = Pattern.matches(pattern, str);
		return returnBoolen;
	}

	public static String getDeviceType(Device _device) {
		if(_device.isNormal() == true) {
			return "P";	// pc
		} else if(_device.isMobile() == true) {
			return "M";	// mobile
		} else if(_device.isTablet() == true) {
			return "T";	// tablet
		}
		return "P";
	}

	public static String getDeviceType(HttpServletRequest req) {
	    String userAgent = req.getHeader("User-Agent").toUpperCase();

	    if(userAgent.indexOf("MOBILE") > -1) {
	        if(userAgent.indexOf("PHONE") == -1) {
	        	return "M";
	        } else {
	        	return "T";
	        }
	    } else {
	    	return "P";
	    }
	}

	public static String getBrower(HttpServletRequest req) {
		String brower = null;
		String agent = req.getHeader("User-Agent").toUpperCase();
		
		if (agent != null) {
		   if (agent.indexOf("TRIDENT") > -1) {
		      brower = "MSIE";
		   } else if (agent.indexOf("CHROME") > -1) {
		      brower = "CHROME";
		   } else if (agent.indexOf("OPERA") > -1) {
		      brower = "OPERA";
		   } else if (agent.indexOf("IPHONE") > -1 && agent.indexOf("MOBILE") > -1) {
		      brower = "IPHONE";
		   } else if (agent.indexOf("ANDROID") > -1 && agent.indexOf("MOBILE") > -1) {
		      brower = "ANDROID";
		   }
		}

		return brower;
	}
	
	public static String getEncMD5(String txt) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(txt.getBytes());
		byte byteData[] = md.digest();

		//convert the byte to hex format method 1
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}
}

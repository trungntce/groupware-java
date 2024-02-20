package kr.co.hs.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

import kr.co.hs.common.dto.PageDTO;


public class TagUtils {
	private static NumberFormat sf = NumberFormat.getInstance();
	
	public static String nl2br(String str) {
		return str.replaceAll("(\r\n|\r|\n|\n\r)", "<br />");
	}

	public static Locale getLocale() {

		if ( WebUtils.getSessionAttribute( SecuritySession.getCurrentRequest(), SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null ) {
			return Locale.ENGLISH;
		}

		return (Locale)WebUtils.getSessionAttribute( SecuritySession.getCurrentRequest(), SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);

	}

	public static Object getMapValue(HashMap<String, Object> _hm, String _key) {
		return _hm.get(_key);
	}

	public static Object getValue(List<Object> list, Integer _key) {
		return list.get(_key);
	}

	@SuppressWarnings("static-access")
	public static Boolean findGame(String data, Integer _key) {
		if( data.indexOf( new String().format("%02d", _key) ) > 0 )
			return true;
		return false;
	}

	public static Integer getSno(PageDTO pageDTO, Integer index){
		return pageDTO.getTotal() - (pageDTO.getPage() - 1) * pageDTO.getRows() - index;
	}

	public static String urlEncode(String value) throws UnsupportedEncodingException {
	    return URLEncoder.encode(value, "UTF-8");
	}

	public static String urlDecode(String value) throws UnsupportedEncodingException {
		return URLDecoder.decode(value, "UTF-8");
	}

	public static String getRealUrl() {
		String url = SecuritySession.getCurrentRequest().getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE).toString();

		if( url == null || url.isEmpty() )
			return "";

		return url.substring(0, url.lastIndexOf("/") );
	}
	public static String getRelateiveUrl() {
		return (String)SecuritySession.getCurrentRequest().getAttribute("javax.servlet.forward.servlet_path");
	}

	public static String getRelateiveUrlPath(Integer nlen) {
		String _url = (String)SecuritySession.getCurrentRequest().getAttribute("javax.servlet.forward.servlet_path");
		String[] arrUrl = _url.split("/");
		String ret = "";
		for(int i = 0 ; i < arrUrl.length - nlen ; i++ ) {
			ret += arrUrl[i]+"/";
		}
		return ret;
	}

	public static String getQuery() {
		return SecuritySession.getQuery();
	}

	public static String getMoneyColor(BigDecimal money) {

		StringBuffer sb = new StringBuffer();

		if( money.divide(new BigDecimal(1000000) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#ff0000\">").append( money.divide(new BigDecimal(1000000) ).intValue() ).append(",</span>");
		}

		if( money.divide(new BigDecimal(100000) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#00b790\">").append( money.remainder( new BigDecimal(1000000) ).divide( new BigDecimal(100000)).intValue() ).append("</span>");
		}

		if( money.divide(new BigDecimal(10000) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#000\">").append( money.remainder( new BigDecimal(100000) ).divide( new BigDecimal(10000)).intValue() ).append("</span>");
		}

		if( money.divide(new BigDecimal(1000) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#000\">").append( money.remainder( new BigDecimal(10000) ).divide( new BigDecimal(1000)).intValue() ).append(",</span>");
		}

		if( money.divide(new BigDecimal(100) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#000\">").append( money.remainder( new BigDecimal(1000) ).divide( new BigDecimal(100)).intValue() ).append("</span>");
		}

		if( money.divide(new BigDecimal(10) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#000\">").append( money.remainder( new BigDecimal(100) ).divide( new BigDecimal(10)).intValue() ).append("</span>");
		}

		if( money.divide(new BigDecimal(1) ).intValue() > 0 ) {
			sb.append("<span style=\"color:#000\">").append( money.remainder( new BigDecimal(10) ).divide( new BigDecimal(1)).intValue() ).append("</span>");
		}

		return sb.toString();
	}

	public static String getBankColor(String bank) {
		StringBuffer sb = new StringBuffer();

		bank = bank.replaceAll("-", "");
		int len = bank.length();

		for( int i = 0; i*4 < len ; i++) {
			if( (i+1)* 4 < len ) {

				if( i % 2 == 0 ) {
					sb.append("<span style=\"color:#ff0000\">").append( bank.substring(i*4, (i+1)*4 ) ).append("</span>-");
				}else {
					sb.append("<span style=\"color:#00b71c\">").append( bank.substring(i*4, (i+1)*4 ) ).append("</span>-");
				}
			}
			else {
				if( i % 2 == 0 ) {
					sb.append("<span style=\"color:#ff0000\">").append( bank.substring(i*4, len ) ).append("</span>");
				}else {
					sb.append("<span style=\"color:#00b71c\">").append( bank.substring(i*4, len ) ).append("</span>");
				}
			}
		}

		return sb.toString();
	}
	

	public static String getGameResultSpan(String gameResult) {
		if(gameResult == null || gameResult.length() == 0){
			return "";
		}else{
			StringBuffer sb = new StringBuffer();
			
			String[] arr = gameResult.split(",");
			
			String spanPrefix = "<span class='";
			String spanMiddle = "'>";
			String spanSuffix = "</span> ";
			for(int i =0 ; i < arr.length; i ++){
				sb.append(spanPrefix + arr[i].toLowerCase() + spanMiddle + arr[i] + spanSuffix);
			}
			return sb.toString();
		}
	}
	
	public static String getCurrencySymbol(String currencyStr,Locale _locale) {
		Locale localeTemp = null;
		if(_locale.getLanguage().equals("ko"))
			localeTemp = new Locale("ko","KR");
		if(_locale.getLanguage().equals("ch"))
			localeTemp = new Locale("zh","CN");
		if(_locale.getLanguage().equals("jp"))
			localeTemp = new Locale("ja","JP");
		if(_locale.getLanguage().equals("en"))
			if(currencyStr.equals("PHP"))
				localeTemp = new Locale("en","PH");
			else
				localeTemp = new Locale("en","US");
		
		String symbol = Currency.getInstance(currencyStr).getSymbol(localeTemp);
		
		return symbol;
	}
}

package kr.co.hs.common.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class StringUtil extends StringUtils {

    public static String splitByLength(String org,int length,String separator){
            int size = org.length();
            int root = size / length;
            if(root==0) return org;
            int in = 0;
            StringBuilder buff = new StringBuilder();
            for(int i=0;i<root;i++){
                    buff.append(org.substring(in, in+length));
                    buff.append(separator);
                    in+= length;
            }
            buff.append(org.substring(in, size));
            return buff.toString();
    }

    public static String[] splitEachLength(String org,int[] eachLength,int offset){
            String[] result = new String[eachLength.length];
            int nowLength = 0;
            for(int i=0;i<eachLength.length;i++){
                    int length = eachLength[i];
                    int subLength = nowLength + length;
                    try {
                            if(org.length() < subLength) result[i] = org.substring(nowLength, eachLength.length);
                            else result[i] = org.substring(nowLength, subLength);
                    } catch (Exception e) {
                            throw new RuntimeException(e);
                    }
                    nowLength += length + offset;
            }
            return result;
    }

    public static String[] splitEachLength(String org,List<Integer> eachLength,int offset){
            String[] result = new String[eachLength.size()];
            int nowLength = 0;
            int size = eachLength.size();
            for(int i=0;i<size;i++){
                    int length = eachLength.get(i);
                    int subLength = nowLength + length;
                    if(org.length() < subLength) result[i] = org.substring(nowLength, org.length());
                    else result[i] = org.substring(nowLength, subLength);
                    nowLength += length + offset;
            }
            //if(org.length() != (nowLength-1)) throw new RuntimeException(
                            //"["+org.length()+"] / ["+(nowLength-1)+"] : Length is not match");
            return result;
    }

    public static void removeNullAndTrim(String[] strings){
            for(int i=0;i<strings.length;i++){
                    String each = strings[i];
                    if(each==null) strings[i] = "";
                    else strings[i] = each.trim();
            }
    }

	public static String removeBracketAndTag(String str) {

		if (str == null) {
			return "";
		}

		Matcher mat;

		Pattern script = Pattern.compile("<(no)?script[^>]*>.*?</(no)?script>", Pattern.DOTALL);
		mat = script.matcher(str);
		str = mat.replaceAll("");

		Pattern style = Pattern.compile("<style[^>]*>.*</style>", Pattern.DOTALL);
		mat = style.matcher(str);
		str = mat.replaceAll("");

		Pattern tag = Pattern.compile("<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
		mat = tag.matcher(str);
		str = mat.replaceAll("");

		Pattern ntag = Pattern.compile("<\\w+\\s+[^<]*\\s*>");
		mat = ntag.matcher(str);
		str = mat.replaceAll("");

		Pattern Eentity = Pattern.compile("&[^;]+;");
		mat = Eentity.matcher(str);
		str = mat.replaceAll("");

		Pattern wspace = Pattern.compile("\\s\\s+");
		mat = wspace.matcher(str);
		str = mat.replaceAll("");

		Pattern bracket = Pattern.compile("\\[.*?\\]");
		mat = bracket.matcher(str);
		str = mat.replaceAll("");

		str = str.replaceAll("\\s", "");
		str = str.replaceAll("▲", "");
		str = str.replaceAll("▼", "");

		return str;
	}

	public static String iterateStr(String str,String seperator,int size) {
	    StringBuilder b = new StringBuilder();
	    for(int i=0;i<size;i++){
	        if(i!=0) b.append(seperator);
	        b.append(str);
	    }
	    return b.toString();
	}

	public static boolean isMatch(String body, String... strs) {
	    for (String str : strs)
	        if (StringUtil.contains(body, str)) return true;
	    return false;
	}

	public static boolean isStartsWithAny(String body, String... prefix) {
	    if(body==null) return false;
	    for (String each : prefix)
	            if (StringUtil.startsWith(body, each)) return true;
	    return false;
	}

	public static boolean isMatchIgnoreCase(String body, String... strs) {
	    body = body.toUpperCase();
	    for (String str : strs)
	        if (StringUtil.contains(body, str.toUpperCase())) return true;
	    return false;
	}

	public static boolean isEquals(String body, String... strs) {
	    if (body == null) return false;
	    for (String str : strs)
	        if (body.equals(str)) return true;
	    return false;
	}

	public static boolean isEqualsIgnoreCase(String body, String... strs) {
	    if (body == null) return false;
	    for (String str : strs)
	        if (body.equalsIgnoreCase(str)) return true;
	    return false;
	}

	public static String getFirst(String str, String pattern) {
	    int index = str.indexOf(pattern);
	    return index == -1 ? str : str.substring(0,index);
	}

	public static String[] getFirstOf(String str, String pattern) {
	    String[] temp = new String[2];
	    int index = str.indexOf(pattern);
	    if (index == -1){
	            temp[0] = str;
	            temp[1] = str;
	            return temp;
	    }
	    temp[0] = str.substring(0, index);
	    temp[1] = str.substring(index + 1);
	    return temp;
	}

	public static String getFirstAfter(String str, String pattern) {
	    return str.substring(str.indexOf(pattern)+1,str.length() );
	}

	public static String getLast(String str, String pattern) {
	    int index = str.lastIndexOf(pattern);
	    if(index==-1) return str;
	    return str.substring(index + 1);
	}

	public static String getExtention(String str) {
	    int index = str.lastIndexOf(".");
	    if (index == -1) return "";
	    return str.substring(index + 1);
	}

	public static String getExtention2(String str) {
	    int index = str.lastIndexOf(".");
	    if (index == -1) return str;
	    return str.substring(index + 1);
	}

	public static String[] getExtentions(String str) {
	    return getLastOf(str,".");
	}

	public static String[] getLastOf(String str,String seperator) {
	    String[] temp = new String[2];
	    int index = str.lastIndexOf(seperator);
	    if (index == -1) return null;
	    temp[0] = str.substring(0, index);
	    temp[1] = str.substring(index + 1);
	    return temp;
	}

	public static String[] getUrlAndExtention(String url) {
	    String[] str = new String[2];
	    int index = url.lastIndexOf(".");
	    if (index < 0) throw new RuntimeException();
	    str[0] = url.substring(1, index);
	    str[1] = url.substring(index + 1);
	    return str;
	}

	public static boolean isHan(char c) {
	    if (Character.getType(c) == Character.OTHER_LETTER) { return true; }
	    return false;
	}

	public static boolean isHanAny(String str) {
	    for (char c : str.toCharArray())
	        if (Character.getType(c) == Character.OTHER_LETTER) { return true; }
	    return false;
	}

	private static final String[] beanMethods = new String[] { "get", "set", "is" };

	public static String getFieldName(String name) {
	    for (String type : beanMethods) {
	        if (name.startsWith(type)) return escapeAndUncapitalize(name, type);
	    }
	    return null;
	}

	private static final String[] getterMethods = new String[] { "get", "is" };

	public static String getterName(String name) {
	    for (String type : getterMethods) {
	        if (name.startsWith(type)) return escapeAndUncapitalize(name, type);
	    }
	    return null;
	}

	private static final String[] setterMethods = new String[] { "set"};

	public static String setterName(String name) {
	    for (String type : setterMethods) {
	        if (name.startsWith(type)) return escapeAndUncapitalize(name, type);
	    }
	    return null;
	}

	public static boolean isBusinessId(String str) {
	    String[] strs = str.split(EMPTY);
	    if (strs.length != 11) return false;
	    int[] ints = new int[10];
	    for (int i = 0; i < 10; i++)
	        ints[i] = Integer.valueOf(strs[i + 1]);
	    int sum = 0;
	    int[] indexs = new int[] { 1, 3, 7, 1, 3, 7, 1, 3 };
	    for (int i = 0; i < 8; i++) {
	        sum += ints[i] * indexs[i];
	    }
	    int num = ints[8] * 5;
	    sum += (num / 10) + (num % 10);
	    sum = 10 - (sum % 10);
	    return sum == ints[9] ? true : false;
	}


	public static boolean isSid(String input) {
	    input = getNumericStr(input);

	    if (input.length() != 13) throw new RuntimeException();

	    String leftSid = input.substring(0, 6);
	    String rightSid = input.substring(6, 13);

	    int yy = Integer.parseInt(leftSid.substring(0, 2));
	    int mm = Integer.parseInt(leftSid.substring(2, 4));
	    int dd = Integer.parseInt(leftSid.substring(4, 6));

	    if (yy < 1 || yy > 99 || mm > 12 || mm < 1 || dd < 1 || dd > 31) return false;

	    int digit1 = Integer.parseInt(leftSid.substring(0, 1)) * 2;
	    int digit2 = Integer.parseInt(leftSid.substring(1, 2)) * 3;
	    int digit3 = Integer.parseInt(leftSid.substring(2, 3)) * 4;
	    int digit4 = Integer.parseInt(leftSid.substring(3, 4)) * 5;
	    int digit5 = Integer.parseInt(leftSid.substring(4, 5)) * 6;
	    int digit6 = Integer.parseInt(leftSid.substring(5, 6)) * 7;

	    int digit7 = Integer.parseInt(rightSid.substring(0, 1)) * 8;
	    int digit8 = Integer.parseInt(rightSid.substring(1, 2)) * 9;
	    int digit9 = Integer.parseInt(rightSid.substring(2, 3)) * 2;
	    int digit10 = Integer.parseInt(rightSid.substring(3, 4)) * 3;
	    int digit11 = Integer.parseInt(rightSid.substring(4, 5)) * 4;
	    int digit12 = Integer.parseInt(rightSid.substring(5, 6)) * 5;

	    int last_digit = Integer.parseInt(rightSid.substring(6, 7));

	    int error_verify = (digit1 + digit2 + digit3 + digit4 + digit5 + digit6 + digit7 + digit8 + digit9 + digit10 + digit11 + digit12) % 11;

	    int sum_digit = 0;
	    if (error_verify == 0) {
	        sum_digit = 1;
	    } else if (error_verify == 1) {
	        sum_digit = 0;
	    } else {
	        sum_digit = 11 - error_verify;
	    }

	    if (last_digit == sum_digit) return true;
	    return false;
	}


	public static String getUnderscore(String str) {

	    char[] chars = str.toCharArray();
	    StringBuffer stringBuffer = new StringBuffer();
	    for (char cha : chars) {
	        if (cha >= 'A' && cha <= 'Z') stringBuffer.append('_');
	        stringBuffer.append(cha);
	    }
	    return stringBuffer.toString().toUpperCase();
	}

	public static String getCamelize(String str) {
	    char[] chars = str.toCharArray();
	    boolean nextCharIsUpper = false;
	    StringBuffer stringBuffer = new StringBuffer();
	    for (char cha : chars) {
	        if (cha == '_' || cha == '-') {
	            nextCharIsUpper = true;
	            continue;
	        }
	        if (nextCharIsUpper) {
	            stringBuffer.append(Character.toUpperCase(cha));
	            nextCharIsUpper = false;
	        } else stringBuffer.append(Character.toLowerCase(cha));
	    }
	    return stringBuffer.toString();
	}

	public static String escapeAndUncapitalize(String str, String header) {
	    return StringUtils.uncapitalize(str.replaceFirst(header, EMPTY));
	}

	public static byte[] getByte(String str) {
	    char[] chs = str.toCharArray();
	    byte[] bytes = new byte[chs.length];
	    for (int i = 0; i < chs.length; i++) {
	        bytes[i] = (byte) chs[i];
	    }
	    return bytes;
	}

	public static String getStr(byte[] bytes) {
	    char[] chars = new char[bytes.length];
	    for (int i = 0; i < bytes.length; i++) {
	        chars[i] = (char) bytes[i];
	    }
	    return String.valueOf(chars);
	}

	public static String getBundleStr(List<String> bundle, String defaultValue) {
	    if (bundle == null || bundle.size() == 0) return defaultValue;
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < bundle.size(); i++) {
	        stringBuffer.append((i == 0) ? EMPTY : ",");
	        stringBuffer.append("'");
	        stringBuffer.append(bundle.get(i));
	        stringBuffer.append("'");
	    }
	    return stringBuffer.toString();
	}

	public static int getCharCount(String str, char ch) {
	    int count = 0;
	    for (int i = 0; i < str.length(); i++) {
	        if (str.charAt(i) == ch) {
	            count++;
	        }
	    }
	    return count;
	}

	public static int getStrLength(String str) {
	    int strLength = 0;
	    int hangleHtmlWidth = 20;
	    int elseHtmlWidth = 12;
	    for (int i = 0; i < str.length(); i++) {
	        if (Character.getType(str.charAt(i)) == 5) {
	            strLength += hangleHtmlWidth;
	        } else {
	            strLength += elseHtmlWidth;
	        }
	    }
	    return strLength;
	}

	public static Integer plusForInteger(String str, int str2) {
	    return Integer.parseInt(str)+str2;
	}

    public static String[] addArray(String[] org,String ... args){
            if(org==null) return args;
            String[] result = new String[org.length + args.length];
            for(int i=0;i<org.length;i++){
                    result[i] = org[i];
            }
            for(int i=0;i<args.length;i++){
                    result[org.length+i] = args[i];
            }
            return result;
    }

	public static String getNumericStr(Object str) {
	    if (str == null) return null;
	    return getNumericStr(str.toString());
	}

	public static String getNumericStr(String str) {
	    if (str == null) return EMPTY;
	    StringBuffer result = new StringBuffer();
	    int dotCount = 0;
	    for (int i = 0; i < str.length(); i++) {
	        char c = str.charAt(i);
	        if ((c >= '0' && c <= '9')) result.append(c);
	        else if ((c == '.' && dotCount < 1)) {

	            result.append(c);
	            dotCount++;
	        }
	    }
	    return result.toString();
	}

	public static BigDecimal getDecimal(String str) {
	    if (str == null || str.equals(EMPTY)) return BigDecimal.ZERO;
	    boolean minus = str.startsWith("-");
	    String temp = StringUtil.getNumericStr(str);
	    if (temp.equals(EMPTY)) return BigDecimal.ZERO;
	    if(minus) temp = "-" + temp;
	    return new BigDecimal(temp);
	}

	public static double getDoubleValue(String str) {
	    return Double.parseDouble(StringUtil.getNumericStr(str));
	}

	public static int getIntValue(String str) {
	    return Integer.parseInt(StringUtil.getNumericStr(str));
	}

	public static String joinTemp(List<?> list, String seperator) {
	    StringBuffer stringBuffer = new StringBuffer();
	    boolean first = true;
	    for (Object string : list) {
	        if (!first) stringBuffer.append(seperator);
	        else first = false;
	        stringBuffer.append(string);
	    }
	    return stringBuffer.toString();
	}

	public static <T> String joinTemp(T[] list, String seperator) {
	    StringBuffer stringBuffer = new StringBuffer();
	    boolean first = true;
	    for (Object string : list) {
	        if (!first) stringBuffer.append(seperator);
	        else first = false;
	        stringBuffer.append(string);
	    }
	    return stringBuffer.toString();
	}

	public static boolean isEmptyAny(String ... objs) {
	    for(String each : objs) if(isEmpty(each)) return true;
	    return false;
	}

	public static String nvl(String str) {
	    return nvl(str, EMPTY);
	}
	public static String nvlObject(Object obj,String escape) {
	    if(obj==null) return escape;
	    return nvl(obj.toString(), escape);
	}

	public static String nvl(String str, String defaultStr) {
	    return StringUtils.isEmpty(str) ? defaultStr : str.trim();
	}

	public static Integer nvl(Integer integer) {
	    return (integer == null) ? 0 : integer;
	}

	public static Integer nvl(String str, Integer defaultint) {
	    return StringUtils.isEmpty(str) ? defaultint : Integer.parseInt(str.trim());
	}

	public static String toString(Object str) {
	    if (str == null) return EMPTY;
	    return str.toString();
	}

	public static String stripHTML(String htmlStr) {
		Pattern p = Pattern.compile("<(?:.|\\s)*?>");
		Matcher m = p.matcher(htmlStr);
		return m.replaceAll("");
	}

	/**
	 * @example getRandomString(4) ==> AA22
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length){
		StringBuffer sb = new StringBuffer();
		Random rnd = new Random();
		for(int i=0; i<length; i++){
			int rIndex = rnd.nextInt(2);
		    switch (rIndex) {
		    case 0:
		        // A-Z
		    	sb.append((char) ((rnd.nextInt(26)) + 65));
		        break;
		    case 1:
		        // 0-9
		    	sb.append((rnd.nextInt(10)));
		        break;
		    }
		}

		return sb.toString();
	}

	public static String getRandomInt(int length){
		StringBuffer sb = new StringBuffer();
		Random rnd = new Random();
		for(int i=0; i<length; i++){
			sb.append((rnd.nextInt(10)));
		}

		return sb.toString();
	}

	public static String map2str(Map<String, String[]> map) {

		if (map == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer();

		for (String key : map.keySet()) {
			Object val = map.get(key);

			if (val == null) {
				sb.append(key + " : null");
			} else if (val instanceof String) {
				sb.append(key + " : " + val+",");
			} else if (val instanceof String[]) {
				String[] arr = (String[]) val;
				if (arr.length == 1) {
					sb.append(key + " : " + arr[0]+",");
				} else {
					for (int i = 0; i < arr.length; i++) {
						sb.append(key + "[" + i + "] : " + arr[i]+",");
					}
				}
			} else {
				sb.append(key + " : " + val.toString());
			}
		}

		return sb.toString();

	}

}

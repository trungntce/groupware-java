package kr.co.hs.common.crypto;
import java.security.NoSuchAlgorithmException;

public class SHA256Encryptor {
	
	public static String encryptor(String str) {
		if(str == null )
			return null;
		
		java.security.MessageDigest sh;
		String ret = "";
		try {
			sh = java.security.MessageDigest.getInstance("SHA-256");
		
			sh.update(str.getBytes());
			
			byte byteData[] = sh.digest();
			
			StringBuffer sb = new StringBuffer();
			
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			ret = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			ret = null;
		}
			
		return ret;
	}
}
package kr.co.hs.common.util;

import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;


public class CryptoUtil {
	private final static String PASS_PHRASE = "passPhrase";
	private final static String SALT = "18b00b2fc5f0e0ee40447bba4dabc952"; 
	private final static String IV = "4378110db6392f93e95d5159dabdee9b";
	
	private final static Integer ITERATION_COUNT = 99;
	private final static Integer KEY_SIZE = 128;

	/**
	 * @param plainText
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String plainText) throws Exception {        
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1"); 
		KeySpec spec = new PBEKeySpec(PASS_PHRASE.toCharArray(), Hex.decodeHex(SALT.toCharArray()), ITERATION_COUNT, KEY_SIZE); 
		SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES"); 
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
		cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(Hex.decodeHex(IV.toCharArray()))); 
		byte[] encrypted = cipher.doFinal(plainText.getBytes("UTF-8")); 
		
		return new String(Base64.encodeBase64(encrypted));

	}

	/**
	 * @param cipherText
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cipherText) throws Exception {        
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(PASS_PHRASE.toCharArray(), Hex.decodeHex(SALT.toCharArray()), ITERATION_COUNT, KEY_SIZE);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");        
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(Hex.decodeHex(IV.toCharArray())));        
        byte[] decrypted = cipher.doFinal(Base64.decodeBase64(cipherText));        
        
        return new String(decrypted, "UTF-8");
    }
}

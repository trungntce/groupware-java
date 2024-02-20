package kr.co.hs.common.crypto;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kr.co.hs.common.util.ByteUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AES128Cipher {

	private static final String basicKey = "gotlswldkdleoqkr";

	public static String encrypt(String plain) {

		try {
			if(plain == null || plain.isEmpty()) {
				return "";
			} else {
				IvParameterSpec ivParameterSpec = new IvParameterSpec(basicKey.substring(0, 16).getBytes());
				Key keySpec = new SecretKeySpec(basicKey.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
				return ByteUtils.toHexString(cipher.doFinal(plain.getBytes(StandardCharsets.UTF_8)));
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decrypt(String cryptogram) {

		try {
			if(cryptogram == null || cryptogram.isEmpty()) {
				return "";
			} else {
				IvParameterSpec ivParameterSpec = new IvParameterSpec(basicKey.substring(0, 16).getBytes());
				Key keySpec = new SecretKeySpec(basicKey.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
				return new String(cipher.doFinal(ByteUtils.toBytesFromHexString(cryptogram)));
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}

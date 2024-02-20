package kr.co.hs.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class FileUrlDownload {
	public static String download(String sourceUrl, String targetFilename) {
		FileOutputStream fos = null;
		InputStream is = null;
		String orgNm = "";
		
		try {

			fos = new FileOutputStream(targetFilename);
			
			orgNm = sourceUrl.substring( sourceUrl.lastIndexOf("/")+1);
			
			URL url = new URL(sourceUrl.substring(0, sourceUrl.lastIndexOf("/")) + "/" +  URLEncoder.encode( orgNm, "UTF-8"));
			URLConnection urlConnection = url.openConnection();
			
			
			
			
			is = urlConnection.getInputStream();
			byte[] buffer = new byte[1024];
			int readBytes;
			while ((readBytes = is.read(buffer)) != -1) {
				fos.write(buffer, 0, readBytes);
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new File(targetFilename).getName();
	}
}


package kr.co.hs.common.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.hs.common.dao.ICommonDao;
import kr.co.hs.common.dto.FileDTO;
import kr.co.hs.common.util.SecuritySession;

@Service
public class FileService {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(FileService.class);
	public static String uploadPath = "upload";

	@Autowired ICommonDao commonDao;

	MimetypesFileTypeMap mimeMap = new MimetypesFileTypeMap();

	private String patten = new String("yyyy").concat("/").concat("MM").concat("/").concat("dd");
	private SimpleDateFormat sf = new SimpleDateFormat(patten);

	public String preparePath(String path) {
		File savePath = new File(SecuritySession.getRealPath()+File.separator+ uploadPath + File.separator + path);
		if( !savePath.exists() )
			savePath.mkdirs();
		return SecuritySession.getRealPath() + uploadPath + File.separator + path;
	}

	public static String getRandomMD5() {
		String result = null;
		try {
			result = DigestUtils.md5DigestAsHex( String.valueOf( new Random().nextInt(1000000)).getBytes("UTF-8") );
		}catch(UnsupportedEncodingException e) {
			throw new RuntimeException("can not make rand string");
		}
		return result;
	}

	public String getFullPath() {
		return SecuritySession.getRealPath() + File.separator + uploadPath + File.separator;
	}

 	public void uploadFile(MultipartFile file, IFileUpload upload) {

		try {

			String path = sf.format(new Date() );
			preparePath( path );

			String newName = "";
			if( file.getOriginalFilename().lastIndexOf(".") > 0 )
				newName = getRandomMD5()+file.getOriginalFilename().substring( file.getOriginalFilename().lastIndexOf(".") );
			else
				newName = getRandomMD5();

			File saveFile = new File(SecuritySession.getRealPath() + "/" + uploadPath+ "/" + path + "/" +  newName );

			BufferedInputStream bis = new BufferedInputStream( file.getInputStream() );
			BufferedOutputStream bos = new BufferedOutputStream( new FileOutputStream(saveFile) );

			int byteRead = 0;
			byte[] buffer = new byte[8192];
			while( (byteRead = bis.read(buffer, 0, 8192) ) != -1 ) {
				bos.write(buffer, 0, byteRead);
			}

			bos.close();
			bis.close();

			upload.processUpload(file, newName, path);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}


	public void downloadFile(IFileDownload download, HttpServletResponse response) {
		FileInputStream fis = null;
		ServletOutputStream out = null;

		try {
			out = response.getOutputStream();

			FileDTO dto = download.getFileDTO();

			File file = new File( SecuritySession.getRealPath()+"/"+ uploadPath+ "/" + dto.getPath() + "/" +  dto.getNewName() );


			if( !file.exists() ) {
				String str = "<script>alert('File does not exist.'); history.back(-1);</script>";
				response.setContentType("text/html; charset=UTF-8");
				response.getOutputStream().write(str.getBytes());
				return;
			}

			fis = new FileInputStream(file);

			String fileName = encodeFileName( dto.getOrgName() );


			if( dto.getFileSize() == 0 ) {
				response.setContentLength( (int)file.length() );
				response.setHeader("Content-Length", "" + (int)file.length() );
			}
			else {
				response.setContentLength( (int)dto.getFileSize() );
				response.setHeader("Content-Length", "" + dto.getFileSize() );
			}

			response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=\""+fileName+"\";");
			response.setHeader("Content-Transfer-Encoding", "binary;");

			response.setHeader("Pragma", "no-cache;");
			response.setHeader("Expires", "-1;");

			int byteRead = 0;
			byte[] buffer = new byte[8192];
			while( (byteRead = fis.read(buffer, 0, 8192) ) != -1 ) {
				out.write(buffer, 0, byteRead);
			}

			out.flush();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			if( out != null )
				try { out.close();	}catch(IOException e) {}
			if( fis != null )
				try { fis.close();	}catch(IOException e) {}
		}
	}



	private String encodeFileName(String filename) {
		try {
			return URLEncoder.encode(filename, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}

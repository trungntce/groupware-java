package kr.co.hs.common.util;

import kr.co.hs.common.aop.SaveRequestProcessor;
import kr.co.hs.emp.dto.EmpDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SecuritySession {


	@SuppressWarnings("rawtypes")
	public static String Convert(HttpServletRequest request) {
		Map map = request.getParameterMap();
		Enumeration paramName = request.getParameterNames();

		StringBuffer sf = new StringBuffer();

		for (int i = 0; i < map.size(); i++) {
			String strName = (String) paramName.nextElement();

			for (int j = 0; j < ((String[]) map.get(strName)).length; j++) {
				String strValue = ((String[]) map.get(strName))[j];
				sf.append(strName + "=" + strValue + "&");
			}
		}

		return sf.toString();
	}

	public static List< HashMap<String, String>> ConvertParameter(HttpServletRequest request) {
		List< HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();

		String param = Convert(request);

		for(String data : param.split("&") ) {
			String[] items = data.split("=");
			if( items.length == 1 )	continue;
			HashMap<String, String> hm = new HashMap<String, String>();


			if( !items[0].startsWith("^") ) continue;

			hm.put("name", items[0].substring(1) );
			hm.put("value", items[1]);

			list.add( hm);
		}


		return list;


	}

	public static Authentication getAuthenCation() {
		if( SecurityContextHolder.getContext().getAuthentication() == null )
			return null;

		return SecurityContextHolder.getContext().getAuthentication();
	}

	public static EmpDTO getCurrentMember() {

		if( SecurityContextHolder.getContext().getAuthentication() == null ) {
			return new EmpDTO();
		}
		

		Object pricial = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		/*if( pricial instanceof UserDetail ) {
			return ((UserDetail)pricial).getMember();
		}*/

		return new EmpDTO();
	}

	@SuppressWarnings("deprecation")
	public static String getRealPath() {
		//StringBuffer sb = new StringBuffer();
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();

		return sra.getRequest().getRealPath("");
	}

	public static List<MultipartFile> getRequestToFile(HttpServletRequest request, String name) {

		MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
		List<MultipartFile> files = multiRequest.getFiles(name);

		List<MultipartFile> list = new ArrayList<MultipartFile>();
		for( MultipartFile file : files) {
			if( file.getSize() > 0 )
				list.add(file);
		}
		return list;
	}

	public static HttpServletRequest getCurrentRequest() {
		HttpServletRequest servletRequest = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return servletRequest;
	}
	
	public static String getQuery() {
		String str = (String) getCurrentRequest().getSession().getAttribute( SaveRequestProcessor.SAVE_REQUEST );
		if( str == null )	return "";
		return str;
	}}

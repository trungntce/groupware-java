package kr.co.hs.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.util.Optional;


public class UrlUtils {

	private UrlUtils() {}

	public static String getReturnUrl(HttpServletRequest request, HttpServletResponse response) {
		RequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest == null) {
			return request.getSession().getServletContext().getContextPath();
		}
		return savedRequest.getRedirectUrl();
	}
	
	public static String getRefererUrl(HttpServletRequest request) {
		return (String)request.getHeader("Referer");
	}

	/**
	 * Returns the viewName to return for coming back to the sender url
	 *
	 * @param request Instance of {@link HttpServletRequest} or use an injected instance
	 * @return Optional with the view name. Recomended to use an alternativa url with
	 * {@link Optional#orElse(Object)}
	 */
	public static Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
		return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}
}

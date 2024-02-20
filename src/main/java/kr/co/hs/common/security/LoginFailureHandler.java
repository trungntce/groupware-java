package kr.co.hs.common.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	@Autowired private LocaleResolver localeResolver = null;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request
			, HttpServletResponse response
			, AuthenticationException exception)
			throws IOException, ServletException {

		Locale locale = localeResolver.resolveLocale(request);
		String targetUrl = "/login.hs?login=error";
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

}

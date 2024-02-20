package kr.co.hs.common.security;

import kr.co.hs.emp.dto.EmpDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Slf4j
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private String targetUrl = "/main/index.hs";

	@Autowired private SessionRegistry sessionRegistry = null;
	@Autowired private LocaleResolver localeResolver = null;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
		if( authentication.getPrincipal() instanceof kr.co.hs.common.security.UserDetail) {
			EmpDTO emp = ((UserDetail)authentication.getPrincipal() ).getEmp();
			HttpSession session = request.getSession(true);

			log.info("sessionId ===> {}", session.getId());
			sessionRegistry.registerNewSession(session.getId(), authentication.getPrincipal());
			session.setAttribute("_user", emp);
			request.getSession().setMaxInactiveInterval(43200);
		}
		getRedirectStrategy().sendRedirect(request, response,  targetUrl );
	}
}

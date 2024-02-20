package kr.co.hs.common.security;

import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.common.util.SessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class LogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired private SessionRegistry sessionRegistry = null;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		log.info("domain logout =====================> " + request.getServerName());

		String targetUrl = "/login.hs";
		String referer = request.getHeader("referer");
		log.debug("referer ===========>" + referer);

		if(authentication != null) {
			EmpDTO emp = ((kr.co.hs.common.security.UserDetail)authentication.getPrincipal()).getEmp();
			SessionUtils.removeSessionUser(sessionRegistry, emp.getEmpCd());

		}

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}

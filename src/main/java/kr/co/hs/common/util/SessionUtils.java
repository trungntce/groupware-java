package kr.co.hs.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

@Slf4j
public class SessionUtils {

	public static Object getSessionInfo(String name) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return WebUtils.getSessionAttribute(sra.getRequest(), name);
	}

	public static <T> T getSessionInfo(String name, Class<T> type) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		return type.cast(WebUtils.getSessionAttribute(sra.getRequest(), name));
	}

	public static void setSessionInfo(String name, Object model) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

		if (WebUtils.getSessionAttribute(sra.getRequest(), name) != null)
			WebUtils.setSessionAttribute(sra.getRequest(), name, null);

		WebUtils.setSessionAttribute(sra.getRequest(), name, model);
	}

	public static void cleanSession(String name) {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		WebUtils.setSessionAttribute(sra.getRequest(), name, null);
	}

	public static void removeSessionUser(SessionRegistry sessionRegistry, String sessionId) {

		log.debug("sessionRegistry size ============>" + sessionRegistry.getAllPrincipals().size() + ", sessionId =====>"+ sessionId);

		Object obj2 = sessionRegistry.getSessionInformation(sessionId);

		if (obj2 instanceof SessionInformation) {
			SessionInformation sf = (SessionInformation) obj2;
			sf.expireNow();

			log.debug("session removed ======>" + sessionId);
		}
	}

	public static void removeSessionUser(SessionRegistry sessionRegistry, Integer mbno) {

		log.debug("sessionRegistry size ============>" + sessionRegistry.getAllPrincipals().size() + ", mbno =====>"+ mbno);

		/*for (Object obj : sessionRegistry.getAllPrincipals()) {
			if (obj instanceof UserDetail) {
				UserDetail userDetail = (UserDetail) obj;
				log.debug("userDetail ===	"+userDetail.getMember());

				if (userDetail.getMember().getMbno().intValue() == mbno.intValue()) {
					log.debug("userDetail.getMember().getMbno().intValue() ===	"+userDetail.getMember().getMbno().intValue());
					for(SessionInformation info : sessionRegistry.getAllSessions(obj, true)) {
						log.debug("session removed ====== {}, isExpired === {}" , mbno, info.isExpired());
						info.expireNow();
					};
				}
			}
		}*/
	}
}

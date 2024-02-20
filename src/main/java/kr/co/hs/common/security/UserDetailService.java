package kr.co.hs.common.security;

import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.common.util.MessageUtil;
import kr.co.hs.common.util.SecuritySession;
import kr.co.hs.emp.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

@Slf4j
@Service
public class UserDetailService implements UserDetailsService, Serializable {
	private static final long serialVersionUID = 5908966188457218768L;

	@Autowired private EmpService empService;
	@Autowired private LocaleResolver localeResolver = null;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
		HttpServletRequest request = SecuritySession.getCurrentRequest();
		EmpDTO emp = null;
		Locale locale = localeResolver.resolveLocale(request);

		try{
			log.debug("id=================>"+id);
			emp = empService.getEmp(id);

			if(emp == null) {
				log.debug("emp is null =================>"+request.getLocale());
				throw new BadCredentialsException(MessageUtil.getMessage("error.code.50", locale)+"[0]." );
			}

		}catch(DataAccessException dae){
			dae.printStackTrace();
			log.error("[ERROR]==>", dae);
			throw new DataAccessException("ERROR") {
				private static final long serialVersionUID = 1L;
			};
		}

		return new kr.co.hs.common.security.UserDetail(emp);
	}

}

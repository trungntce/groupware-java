package kr.co.hs.common.security;

import com.google.gson.Gson;
import kr.co.hs.common.crypto.SHA256Encryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import sun.security.provider.SHA;

@Slf4j
public class AuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
	@Autowired private UserDetailsService userDetailsService = null;

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

		String password = authentication.getCredentials().toString();
		if (password.length() == 64) {
			password = authentication.getCredentials().toString();

		} else {
			password = SHA256Encryptor.encryptor(password);
		}

		if (!password.equals(userDetails.getPassword())) {
			throw new AuthenticationServiceException(
					messages.getMessage("AbstractUserDetailsAuthenticationProvider.badPassword", "No available"));
		}
	}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		UserDetails loadedUser = null;
		try {

			log.error("@@@ {}", new Gson().toJson(SecurityContextHolder.getContext().getAuthentication()));
			// log.error("@@@ {}", new Gson().toJson(SecurityContextHolder.getContext().getAuthentication().getDetails()));

			loadedUser = userDetailsService.loadUserByUsername(username);
			
		} catch (UsernameNotFoundException notFound) {
			throw notFound;
		} catch (Exception repositoryProblem) {
			throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
		}

		if (loadedUser == null) {
			throw new AuthenticationServiceException(
					"UserDetailsService returned null, which is an interface contract violation");
		}
		return loadedUser;
	}

}

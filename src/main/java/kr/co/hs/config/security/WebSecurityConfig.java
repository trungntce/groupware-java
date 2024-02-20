package kr.co.hs.config.security;

import kr.co.hs.common.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
				.antMatchers("/resources/**")
				.antMatchers("/upload/**")
				.antMatchers("/favicon.ico")
				.antMatchers("/api/**")
//				.antMatchers("/ws")
//				.antMatchers("/signal/*")
				.and()
				.expressionHandler(webExpressionHandler())
				;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//CSRF (Cross Site Request Forgery)
//		WhiteListedAllowFromStrategy whiteListedAllowFromStrategy = new WhiteListedAllowFromStrategy(Arrays.asList("casino9.com"));
//		http.csrf().disable().headers().addHeaderWriter(new XFrameOptionsHeaderWriter(whiteListedAllowFromStrategy));

		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("UTF-8");
		filter.setForceEncoding(true);
		http.addFilterBefore(filter, CsrfFilter.class);

		http.csrf().disable().headers().frameOptions().disable();


		http
				.authorizeRequests()
				.expressionHandler(webExpressionHandler())
				.antMatchers("/").permitAll()
				.antMatchers("/login.hs").permitAll()
				.antMatchers("/editPassword.hs").permitAll()
				.antMatchers("/error/**").permitAll()
				.antMatchers("/auction/**").permitAll()
				.antMatchers("/approvalForm/**").permitAll()
				 .antMatchers("/**").authenticated()
//				 .antMatchers("/**").hasRole("Y")
				.anyRequest().authenticated()
		;

		http.formLogin()
				.loginPage("/login.hs")
				.defaultSuccessUrl("/main/index.hs", true)
				.failureUrl("/login.hs?login=error")
				.usernameParameter("loginId")
				.passwordParameter("loginPw")
				.successHandler(loginSuccessHandler())
				.failureHandler(loginFailureHandler())
				.loginProcessingUrl("/security/login.hs");

		http.logout()
				.logoutUrl("/logout.hs")
				.logoutSuccessHandler(logoutSuccessHandler())
				.invalidateHttpSession(true);

//		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
		// https://okky.kr/article/275549
		http.sessionManagement()
				.sessionFixation().migrateSession()
				.maximumSessions(1).expiredUrl("/login.hs?login=session")
				.sessionRegistry(sessionRegistry());

//		.sessionAuthenticationErrorUrl("/spring/error?error_code=2")
//		.expiredUrl("/spring/error?error_code=3")
//		.maxSessionsPreventsLogin(true)
//		http.addFilterAfter(new AjaxSessionTimeoutFilter(), ExceptionTranslationFilter.class);
//		http.addFilter(new ConcurrentSessionFilter(sessionRegistry(), new kr.co.hera.config.CustomSessionInformationExpiredStrategy("/error/error.hs?error=session.expire")));

//		http.rememberMe();

//		http.Service(userDetailService);
	}

//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailService);
//		auth.authenticationProvider(authenticationProvider());
//	}
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(userDetailService);
//		auth.authenticationProvider(authenticationProvider());
//	}

//	@Bean
//	public AccessDeniedHandler accessDeniedHandler(){
//	    return new HsAccessDeniedHandler();
//	}

	@SuppressWarnings("deprecation")
	@Bean
	public static NoOpPasswordEncoder passwordEncoder() {
		return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public SessionRegistry sessionRegistry() {
		SessionRegistry sessionRegistry = new SessionRegistryImpl();
		return sessionRegistry;
	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		return new AuthenticationProvider();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new LogoutSuccessHandler();
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler(){
		return new LoginSuccessHandler();
	}

	@Bean
	public LoginFailureHandler loginFailureHandler(){
		return new LoginFailureHandler();
	}

	@Bean
	public SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
		DefaultWebSecurityExpressionHandler defaultWebSecurityExpressionHandler = new DefaultWebSecurityExpressionHandler();
		defaultWebSecurityExpressionHandler.setDefaultRolePrefix("USE_YN_");
		return defaultWebSecurityExpressionHandler;
	}
}

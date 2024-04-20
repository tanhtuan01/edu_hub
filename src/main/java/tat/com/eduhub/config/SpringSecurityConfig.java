package tat.com.eduhub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//import tat.com.eduhub.custom.CustomAuthenticationFailureHandler;
import tat.com.eduhub.custom.CustomAuthenticationSuccessHandler;
import tat.com.eduhub.custom.CustomAuthorizationRequestResolver;
import tat.com.eduhub.service.UserService;

@Configuration
@ComponentScan("tat.com.eduhub.component")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/", "/eh-admin/**", "/dang-ky/**", "/js/**", "/css/**", "/img/**")
				.permitAll()
				.antMatchers("/school-admin/**").hasRole("ADMINSCHOOL")
				.antMatchers("/school-lecturer/**").hasRole("LECTURERSCHOOL")
				.anyRequest()
				.authenticated()
				.and()
				.formLogin()
					.loginPage("/dang-nhap")
					.failureUrl("/dang-nhap?error")
					.successHandler(authenticationSuccessHandler())
					.permitAll()
				.and()
				.logout()
					.invalidateHttpSession(true)
					.clearAuthentication(true)
					.logoutRequestMatcher(new AntPathRequestMatcher("/dang-xuat"))
					.logoutSuccessUrl("/dang-nhap?failure")
					.deleteCookies("JSESSIONID")
					.addLogoutHandler(new SecurityContextLogoutHandler())// Khi người dùng đăng xuất, SecurityContextLogoutHandler sẽ xóa bỏ thông tin về bảo mật (bao gồm thông tin xác thực, quyền truy cập, v.v.) khỏi security context. Điều này đảm bảo rằng người dùng không thể tiếp tục truy cập vào các tài nguyên được bảo vệ sau khi đăng xuất.
					.permitAll()
				.and()
				.oauth2Login()
					.loginPage("/dang-nhap-google")
					.defaultSuccessUrl("/afterGoogleLoginSuccess")
					.authorizationEndpoint()
					.authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository))
				;
	}

	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		RequestCache requestCache = new HttpSessionRequestCache();
		return new CustomAuthenticationSuccessHandler(requestCache);
	}


}

package tat.com.eduhub.config;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import tat.com.eduhub.component.SchoolHelper;
import tat.com.eduhub.component.UserHelper;
//import tat.com.eduhub.custom.CustomAuthenticationFailureHandler;
import tat.com.eduhub.custom.CustomAuthenticationSuccessHandler;
import tat.com.eduhub.custom.CustomAuthorizationRequestResolver;
import tat.com.eduhub.service.TeacherOfSchoolService;
import tat.com.eduhub.service.UserService;

@Configuration
@ComponentScan("tat.com.eduhub.component")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Autowired
	private TeacherOfSchoolService teacherOfSchoolService;
	
	@Autowired
	private UserHelper userHelper;
	
	@Bean
	public SchoolHelper schoolHelper() {
		return new SchoolHelper();
	}
	
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
		http.authorizeRequests()
				.antMatchers("/", "/student/**", "/courses/**" , "/uc/api/**", "/dang-ky/**", "/giang-day/**", "/quan-ly-ctdt/**" , "/js/**", "/css/**", "/img/**")
					.permitAll()
				.antMatchers("/school-admin/**").hasRole("ADMINSCHOOL")
				.antMatchers("/school-lecturer/**").hasRole("LECTURERSCHOOL")
				.antMatchers("/student/**").hasAnyAuthority("ROLE_USER", "ROLE_STUDENT", "ROLE_ADMINSCHOOL", "ROLE_LECTURERSCHOOL", "ROLE_LECTURER")
				.antMatchers("/lecturer/**").hasRole("LECTURER")
				.antMatchers("/eh-admin/**").hasRole("SUPERADMIN")
				.antMatchers("/profile/**", "/buy-now/**").hasAnyAuthority("ROLE_USER", "ROLE_STUDENT", "ROLE_ADMINSCHOOL", "ROLE_LECTURERSCHOOL", "ROLE_LECTURER", "ROLE_SUPERADMIN")
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
				.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.sessionFixation().newSession() // Tạo phiên làm việc mới cho mỗi yêu cầu
				.and()
				.oauth2Login()
					
					.loginPage("/dang-nhap-google")
					.defaultSuccessUrl("/afterGoogleLoginSuccess")
					.authorizationEndpoint()
					.authorizationRequestResolver(new CustomAuthorizationRequestResolver(clientRegistrationRepository))
					.and()
					.userInfoEndpoint().userService(oAuth2UserService())
					;
	}
	
	  private OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
	        DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();
	        return (userRequest) -> {
	            OAuth2User oauth2User = delegate.loadUser(userRequest);
	            
	            // Trích xuất thông tin vai trò từ oauth2User và cung cấp nó cho Spring Security
	            
	            return oauth2User;
	        };
	    }

	private AuthenticationSuccessHandler authenticationSuccessHandler() {
		RequestCache requestCache = new HttpSessionRequestCache();
		return new CustomAuthenticationSuccessHandler(requestCache, userService, teacherOfSchoolService, userHelper);
	}

}		
package tat.com.eduhub.custom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest.Builder;

public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver{

	// thay đổi tham số prompt mặc định để cho phép chọn tài khoản khác để đăng nhập
	// nếu không tài khoản google trước đó đã được đăng nhập sẽ được đăng nhập luôn
	private final OAuth2AuthorizationRequestResolver defaultResolver;
	
	public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
		super();
		// TODO Auto-generated constructor stub
		this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, "/oauth2/authorization");
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
		// TODO Auto-generated method stub
		OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request);
        if (authorizationRequest != null) {
            Builder builder = OAuth2AuthorizationRequest.from(authorizationRequest);
            builder.authorizationUri(authorizationRequest.getAuthorizationUri() + "?prompt=select_account");
            return builder.build();
        }
		return null;
	}

	@Override
	public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
		// TODO Auto-generated method stub
		return null;
	}

}

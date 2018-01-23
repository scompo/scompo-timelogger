package tk.scompo.timelogger.webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.web.filter.OrderedRequestContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableOAuth2Client
public class OauthConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Autowired
	private ResourceServerProperties resourceServerProperties;

	@Autowired
	private AuthorizationCodeResourceDetails authorizationCodeResourceDetails;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.antMatcher("/**")
				// conf
				.authorizeRequests()
				// all for base resources.
				.antMatchers("/", "/**.html", "/**.js").permitAll()
				// all other resources authenticated.
				.anyRequest().authenticated()
				// use logout stuff.
				.and().logout().logoutSuccessUrl("/").permitAll()
				// use a filter to get the google login auth callback.
				.and().addFilterBefore(oauthCallbackFilter(), BasicAuthenticationFilter.class);
	}

	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setFilter(filter);
		filterRegistrationBean.setOrder(-100);
		return filterRegistrationBean;
	}

	private OAuth2ClientAuthenticationProcessingFilter oauthCallbackFilter() {
		OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(authorizationCodeResourceDetails,
				oauth2ClientContext);
		UserInfoTokenServices tokenServices = new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(),
				resourceServerProperties.getClientId());
		OAuth2ClientAuthenticationProcessingFilter oAuth2Filter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login");
		oAuth2Filter.setRestTemplate(oAuth2RestTemplate);
		oAuth2Filter.setTokenServices(tokenServices);
		return oAuth2Filter;
	}
	
	@Bean
	public OrderedRequestContextFilter requestContextFilter() {
	    return new OrderedRequestContextFilter();
	}
}

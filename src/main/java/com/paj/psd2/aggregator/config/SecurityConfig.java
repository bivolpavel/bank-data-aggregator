package com.paj.psd2.aggregator.config;

import com.paj.psd2.aggregator.utils.JwtTokenManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Joe Grandja
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


	private final JwtConfig jwtConfig;
	private JwtTokenManager tokenProvider;

	public SecurityConfig(JwtConfig jwtConfig, JwtTokenManager tokenProvider) {
		this.jwtConfig = jwtConfig;
		this.tokenProvider = tokenProvider;
	}


	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors().and()
				.csrf().disable()
				.formLogin().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
				.and()
				.addFilterBefore(new JwtTokenAuthenticationFilter(jwtConfig, tokenProvider), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests((authorize) -> authorize
					.antMatchers("/css/**").permitAll()
					.antMatchers("/user/**").hasRole("USER")
				)
				.logout().disable();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(userDetails);
	}
}

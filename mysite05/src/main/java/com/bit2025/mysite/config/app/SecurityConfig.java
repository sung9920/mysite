package com.bit2025.mysite.config.app;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

import com.bit2025.mysite.repository.UserRepository;
import com.bit2025.mysite.security.UserDetailsServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return webSecurity -> webSecurity.httpFirewall(new DefaultHttpFirewall());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable())
        .formLogin(formLogin -> {
        	formLogin
        		.loginPage("/user/login")
        		.loginProcessingUrl("/user/auth")
        		.usernameParameter("email")
        		.passwordParameter("password")
        		.defaultSuccessUrl("/")
        		.failureHandler(new AuthenticationFailureHandler() {

					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException exception) throws IOException, ServletException {
						String email = request.getParameter("email");
						request.setAttribute("email", email);
						request
							.getRequestDispatcher("/user/login")
							.forward(request, response);
					}
				});

        })
    	.authorizeHttpRequests(authorizeRequest -> {
    		/* ACL */
    		authorizeRequest
				.requestMatchers(
						new RegexRequestMatcher("^/admin/?.*$", null),
						new RegexRequestMatcher("^/user/update$", null),
						new RegexRequestMatcher("^/board/?(write|delete|modify|reply).*$", null))
				.authenticated().anyRequest().permitAll();

    	});

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncoder);

    	return new ProviderManager(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder(4); // 4 ~ 31
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
    	return new UserDetailsServiceImpl(userRepository);
    }
}
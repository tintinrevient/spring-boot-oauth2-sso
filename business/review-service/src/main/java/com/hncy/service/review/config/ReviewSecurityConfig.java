package com.hncy.service.review.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;

@EnableOAuth2Sso
@Configuration
public class ReviewSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${security.oauth2.client.userLogoutUri}")
    private String logoutUrl;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/", "/login**", "/logout**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessUrl(logoutUrl)
                .deleteCookies("REVIEWSESSION");
    }

}

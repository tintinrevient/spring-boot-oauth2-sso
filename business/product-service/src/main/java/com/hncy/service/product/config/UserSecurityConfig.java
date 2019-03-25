package com.hncy.service.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import java.util.List;
import java.util.ArrayList;

@EnableOAuth2Client
@Configuration
public class UserSecurityConfig {

    @Value("${security.oauth2.client.accessTokenUri}")
    private String accessTokenUrl;

    @Value("${security.oauth2.client.clientId}")
    private String clientId;

    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Autowired(required = false)
    ClientHttpRequestFactory clientHttpRequestFactory;

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        if (clientHttpRequestFactory == null) {
            clientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        }
        return clientHttpRequestFactory;
    }

    @Bean
    @Qualifier("clientOnlyRestTemplate")
    public OAuth2RestOperations clientOnlyRestTemplate() {

        OAuth2RestTemplate template = new OAuth2RestTemplate(fullAccessResourceDetailsClientOnly(), new DefaultOAuth2ClientContext(
                new DefaultAccessTokenRequest()));

        template.setRequestFactory(getClientHttpRequestFactory());
        template.setAccessTokenProvider(clientAccessTokenProvider());

        return template;
    }

    @Bean
    public AccessTokenProvider clientAccessTokenProvider() {
        ClientCredentialsAccessTokenProvider accessTokenProvider = new ClientCredentialsAccessTokenProvider();
        accessTokenProvider.setRequestFactory(getClientHttpRequestFactory());
        return accessTokenProvider;
    }

    @Bean
    @Qualifier("clientOnlyFullAccessDetails")
    public OAuth2ProtectedResourceDetails fullAccessResourceDetailsClientOnly() {
        ClientCredentialsResourceDetails resource = new ClientCredentialsResourceDetails();
        resource.setAccessTokenUri(accessTokenUrl);
        resource.setClientId(clientId);
        resource.setClientSecret(clientSecret);
        resource.setGrantType("client_credentials");

        List<String> scopesList = new ArrayList<String>();
        scopesList.add("user_info");
        resource.setScope(scopesList);

        return resource;
    }

}
package com.hncy.service.product.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.hncy.service.product.model.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.oauth2.client.OAuth2RestOperations;

@RestController
@Component
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${security.oauth2.client.userDataUri}")
    private String userDataUrl;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @RequestMapping(value = "/user/all")
    @HystrixCommand(fallbackMethod = "getDefaultUsers")
    public List<User> getAllUsers() {
        logger.info("user service is called.");

        ResponseEntity<User[]> response = restTemplate.getForEntity(userDataUrl, User[].class);
        return Arrays.asList(response.getBody());
    }

    public List<User> getDefaultUsers() {
        logger.info("fallback user service is called.");
        return new ArrayList<User>();
    }

}
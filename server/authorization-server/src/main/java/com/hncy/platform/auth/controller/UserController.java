package com.hncy.platform.auth.controller;

import java.security.Principal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import java.io.IOException;
import com.hncy.platform.auth.repository.UserRepository;
import com.hncy.platform.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/user/userinfo")
    public Principal userinfo(Principal principal) {
        System.out.println(principal);
        return principal;
    }

    @RequestMapping("/user/all")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping("/user/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

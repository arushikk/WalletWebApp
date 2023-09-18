package com.Example.Wallet.Project;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
public class userController {



@Autowired
UserService userService;
    @PostMapping("/user")
    public void createUser(@RequestBody UserCreateRequest userCreateRequest ) throws JsonProcessingException {

        userService.create(userCreateRequest);
    }


    @GetMapping("/user")
    public User getUserDetails(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();

        User user = (User)authentication.getPrincipal();
       return userService.loadUserByUsername(user.getUsername());

    }

    @GetMapping("/admin/all/users")
    public List<User> getAllUserDetails(){
       return userService.getAll();

    }


}

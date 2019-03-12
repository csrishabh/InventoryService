package com.mongo.demo.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.Product;
import com.mongo.demo.document.User;
import com.mongo.demo.service.CustomUserDetailsService;

@RestController
public class LoginController {
	
	@Autowired
	private CustomUserDetailsService userService;	
	
	
	@GetMapping(value = "/username")
    public ResponseEntity<User> currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        User user = userService.findUserByEmail(principal.getName());
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}

package com.mongo.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mongo.demo.document.AppResponse;
import com.mongo.demo.document.User;
import com.mongo.demo.repo.UserRepository;
import com.mongo.demo.service.CustomUserDetailsService;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	CustomUserDetailsService userService;

	@GetMapping(value = "/getUsers")
    public ResponseEntity<List<String>> getAllUsers() {
		List<User> users = userRepo.findAll();
		return new ResponseEntity<List<String>>(users.stream().map(user -> user.getUsername()).collect(Collectors.toList()),HttpStatus.OK);
    }
	
	@GetMapping("/user/{name}/{type}")
	public List<User> getPersonByNameAndType(@PathVariable("name") String name, @PathVariable("type") String type) {
		if(name.trim().equals("")) {
			return new ArrayList<>();
		}
		List<User> users = userRepo.findByNameStartingWithAndType(name.trim(),type.toUpperCase().trim());
		return users;
	}
	
	@GetMapping("/user/{name}")
	public List<User> getPersonByName(@PathVariable("name") String name) {
		if(name.trim().equals("")) {
			return new ArrayList<>();
		}
		List<User> users = userRepo.findByNameStartingWith(name.trim());
		return users;
	}
	
	@PostMapping("/password/reset")
	public AppResponse<String> resetPassword(@RequestBody HashMap<String, String> param) {
		return userService.resetPassword(param);
	}

}

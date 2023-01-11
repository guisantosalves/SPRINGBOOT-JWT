package com.project.security.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.security.user.User;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    
    @RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<User> Exemple(){
		return null;
	}
}

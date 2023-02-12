package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Reponse.dto.User;

@RestController
@RequestMapping("/api")
public class ApiController {

	// TEXT
	@GetMapping("/text")
	public String text(@RequestParam String account)
	{
		return account;
	}
	
    // JSON
    // request -> object mapper -> object -> method -> object
    // -> object mapper -> json -> response	
	@PostMapping("/json")
	public User json(@RequestBody User user)
	{
		return user;
	}
	
    // Response를 내려줄 때 HTTP Status를 지정해주기
    @PutMapping("/put")
    public ResponseEntity<User> put(@RequestBody User user) {
        // HttpStatus.CREATED == 201
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    } 
}

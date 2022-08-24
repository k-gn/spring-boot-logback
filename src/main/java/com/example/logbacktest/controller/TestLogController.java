package com.example.logbacktest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.logbacktest.config.aop.RequestLogAspect;
import com.example.logbacktest.dto.ApiDataResponse;
import com.example.logbacktest.entity.Users;
import com.example.logbacktest.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class TestLogController {

	private final UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

	@PostMapping("/users")
	public ResponseEntity<?> insert(@RequestBody Users user) {
		return ResponseEntity.ok(ApiDataResponse.of(userService.insert(user)));
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		return ResponseEntity.ok(ApiDataResponse.of(userService.getUser(id)));
	}
}

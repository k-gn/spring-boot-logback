package com.example.logbacktest.service;

import org.springframework.stereotype.Service;

import com.example.logbacktest.constant.ErrorCode;
import com.example.logbacktest.entity.Users;
import com.example.logbacktest.exception.GeneralException;
import com.example.logbacktest.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public Users insert(Users user) {
		return userRepository.save(user);
	}

	public Users getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> new GeneralException(ErrorCode.BAD_REQUEST));
	}
}

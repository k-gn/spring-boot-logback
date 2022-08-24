package com.example.logbacktest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.logbacktest.entity.Users;

public interface UserRepository extends JpaRepository<Long, Users> {
}

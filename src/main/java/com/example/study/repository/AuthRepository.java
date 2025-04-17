package com.example.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.study.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
	boolean existsByEmail(String email);
}

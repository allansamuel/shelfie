package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.GuardianUser;

public interface GuardianUserRepository extends JpaRepository<GuardianUser, Integer>{
	
}

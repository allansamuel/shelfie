package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shelfie.model.GuardianUser;

public interface GuardianUserRepository extends JpaRepository<GuardianUser, Integer>{
	
//	 @Query("SELECT gu FROM guardian_user gu WHERE gu.guardian_user_name = :guardian_user_name")
//	    public GuardianUser getUserByUsername(@Param("guardian_user_name") String username);
	
}

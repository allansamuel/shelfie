package com.shelfie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shelfie.model.ChildProfile;

public interface ChildProfileRepository extends JpaRepository<ChildProfile, Integer>{

	@Query
	List<ChildProfile> findByGuardianUserId(Integer guardianUserId);
}

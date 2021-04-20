package com.shelfie.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shelfie.model.ChildProfile;
import com.shelfie.repository.ChildProfileRepository;

@Controller
@RequestMapping("child_profiles")
public class ChildProfileController {

	@Autowired
	private ChildProfileRepository repo;
	
	@GetMapping
	@ResponseBody
	public List<ChildProfile> getAll(){
		return repo.findAll();
	}
	
	@GetMapping("{childProfileId}")
	@ResponseBody
	public Optional<ChildProfile> getById(@RequestParam Integer childProfileId){
		return repo.findById(childProfileId);
	}
	
	/*
	@GetMapping("getByGuardian/{guardianUserId}")
	@ResponseBody
	public List<ChildProfile> getByGuardianUserId(@RequestParam Integer guardianUserId){
		return repo.findByGuardianUser(guardianUserId);
	}
	*/
}

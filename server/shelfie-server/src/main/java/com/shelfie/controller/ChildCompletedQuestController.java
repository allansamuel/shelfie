package com.shelfie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shelfie.model.ChildProfile;
import com.shelfie.service.ChildCompletedQuestService;


@RestController
@RequestMapping("quest_complete")
public class ChildCompletedQuestController {

	@Autowired
	private ChildCompletedQuestService childCompletedQuestService;
	
	@PostMapping("child_profile/{childProfileId}/quest/{questId}")
	public ResponseEntity<ChildProfile> complete(
			@PathVariable Integer childProfileId,
			@PathVariable Integer questId) throws Exception {
		try {
			return ResponseEntity.ok(childCompletedQuestService.complete(childProfileId, questId));
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@GetMapping("child_profile/{childProfileId}/quest/{questId}")
	public ResponseEntity<Boolean> isCompleted(
			@PathVariable Integer childProfileId,
			@PathVariable Integer questId) throws Exception {
		try {
			return childCompletedQuestService.isQuestCompleted(childProfileId, questId) ?
					ResponseEntity.ok(true) : ResponseEntity.ok(false);
		} catch (Exception exception) {
			throw exception;
		}
	}
}

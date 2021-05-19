package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "child_completed_quests")
public class ChildCompletedQuest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "child_completed_quest_id")
	private Integer childCompletedQuestId;
	
	@ManyToOne
	@JoinColumn(name = "child_profile_id")
	private ChildProfile childProfile;
	
	@ManyToOne
	@JoinColumn(name = "quest_id")
	private Quest quest;

	public ChildCompletedQuest() {
		super();
	}

	public ChildCompletedQuest(Integer childCompletedQuestId, ChildProfile childProfile, Quest quest) {
		super();
		this.childCompletedQuestId = childCompletedQuestId;
		this.childProfile = childProfile;
		this.quest = quest;
	}

	public Integer getChildCompletedQuestId() {
		return childCompletedQuestId;
	}

	public void setChildCompletedQuestId(Integer childCompletedQuestId) {
		this.childCompletedQuestId = childCompletedQuestId;
	}

	public ChildProfile getChildProfile() {
		return childProfile;
	}

	public void setChildProfile(ChildProfile childProfile) {
		this.childProfile = childProfile;
	}

	public Quest getQuest() {
		return quest;
	}

	public void setQuest(Quest quest) {
		this.quest = quest;
	}

}

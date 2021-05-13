package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "quests")
public class Quest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quest_id")
	private Integer questId;

	@NotNull
	@Column(name = "quest_title")
	private String questTitle;

	@NotNull
	@Column(name = "quest_description")
	private String questDescription;

	@NotNull
	@Column(name = "coins_reward")
	private Integer coinsReward;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name = "interactive_book_id")
	private InteractiveBook interactiveBook;
	
	public Quest() {
		super();
	}

	public Quest(Integer questId, String questTitle, String questDescription, Integer coinsReward,
			InteractiveBook interactiveBook) {
		super();
		this.questId = questId;
		this.questTitle = questTitle;
		this.questDescription = questDescription;
		this.coinsReward = coinsReward;
		this.interactiveBook = interactiveBook;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public String getQuestTitle() {
		return questTitle;
	}

	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}

	public String getQuestDescription() {
		return questDescription;
	}

	public void setQuestDescription(String questDescription) {
		this.questDescription = questDescription;
	}

	public Integer getCoinsReward() {
		return coinsReward;
	}

	public void setCoinsReward(Integer coinsReward) {
		this.coinsReward = coinsReward;
	}

	public InteractiveBook getInteractiveBook() {
		return interactiveBook;
	}

	public void setInteractiveBook(InteractiveBook interactiveBook) {
		this.interactiveBook = interactiveBook;
	}
	
}

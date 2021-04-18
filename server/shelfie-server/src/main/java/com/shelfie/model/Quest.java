package com.shelfie.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quests")
public class Quest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quest_id")
	private Integer questId;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "coins_reward")
	private Integer coinsReward;
	
	@ManyToOne
	@Column(name = "interactive_book")
	private InteractiveBook interactiveBook;
	
	public Quest() {
		super();
	}

	public Quest(Integer questId, String title, String description, Integer coinsReward,
			InteractiveBook interactiveBook) {
		super();
		this.questId = questId;
		this.title = title;
		this.description = description;
		this.coinsReward = coinsReward;
		this.interactiveBook = interactiveBook;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

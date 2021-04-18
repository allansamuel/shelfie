package com.shelfie.model;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name = "interactive_books")
public class InteractiveBook {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "interative_book_id")
	private Integer interactiveBookId;
	
	@Lob
	@Column (name = "book_cover")
	private Blob bookCover;
	
	@Column (name = "sinopsys", length = 800)
	private String sinopsys;
	
	@Column (name = "author", length = 255)
	private String author;
	
	@Column (name = "publish_date")
	private Date publishDate;
	
	@Column (name = "price")
	private Integer price;
	
	@Column (name = "avaliable")
	private boolean avaliable;
	
	@Column (name = "title", length = 255)
	private String title;

	@OneToMany
	@JoinColumn(name = "chapter_id")
	private List<Chapter> chapters;
	
	@OneToMany
	@JoinColumn(name = "character_id")
	private List<Character> characters;
	
	@OneToMany
	@JoinColumn(name = "quest_id")
	private List<Quest> quests;
	
	@ManyToMany
	@JoinTable(name = "book_categories", 
	joinColumns = {@JoinColumn(name = "interactive_book_id")}, 
	inverseJoinColumns = {@JoinColumn(name = "category_id")})
	private List<Category> bookCategories;
	
	public InteractiveBook() {
		super();
	}

	public InteractiveBook(Integer interactiveBookId, Blob bookCover, String sinopsys, String author, Date publishDate,
			Integer price, boolean avaliable, String title, List<Chapter> chapters, List<Character> characters,
			List<Quest> quests, List<Category> bookCategories) {
		super();
		this.interactiveBookId = interactiveBookId;
		this.bookCover = bookCover;
		this.sinopsys = sinopsys;
		this.author = author;
		this.publishDate = publishDate;
		this.price = price;
		this.avaliable = avaliable;
		this.title = title;
		this.chapters = chapters;
		this.characters = characters;
		this.quests = quests;
		this.bookCategories = bookCategories;
	}

	public Integer getInteractiveBookId() {
		return interactiveBookId;
	}

	public void setInteractiveBookId(Integer interactiveBookId) {
		this.interactiveBookId = interactiveBookId;
	}

	public Blob getBookCover() {
		return bookCover;
	}

	public void setBookCover(Blob bookCover) {
		this.bookCover = bookCover;
	}

	public String getSinopsys() {
		return sinopsys;
	}

	public void setSinopsys(String sinopsys) {
		this.sinopsys = sinopsys;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public boolean isAvaliable() {
		return avaliable;
	}

	public void setAvaliable(boolean avaliable) {
		this.avaliable = avaliable;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}

	public List<Character> getCharacters() {
		return characters;
	}

	public void setCharacters(List<Character> characters) {
		this.characters = characters;
	}

	public List<Quest> getQuests() {
		return quests;
	}

	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}

	public List<Category> getBookCategories() {
		return bookCategories;
	}

	public void setBookCategories(List<Category> bookCategories) {
		this.bookCategories = bookCategories;
	}

}

package com.shelfie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.shelfie.model.InteractiveBook;
import com.shelfie.repository.InteractiveBookRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping("interactive_book")
public class InteractiveBookController {

	@Autowired
	private InteractiveBookRepository interactiveBookRepository;
	
	@GetMapping("/page/{pageNumber}")
	public ResponseEntity<List<InteractiveBook>> getAll(@PathVariable int pageNumber) {
		
		try {
			pageNumber = pageNumber-1;
			Page<InteractiveBook> page = interactiveBookRepository
					.findAllByOrderByPublishDateDescTitleAsc(PageRequest.of(pageNumber, 8));
			List <InteractiveBook> interactivebooks = page.getContent();
			return ResponseEntity.ok().body(interactivebooks);
	
		} catch (Exception exception) {
			throw exception;
		}	
	}
	
	@GetMapping("{id}")
	public ResponseEntity<InteractiveBook> getById(@PathVariable Integer id) throws Exception {
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		return ResponseEntity.ok().body(interactiveBook);		
	}
	
	@GetMapping("search/{searchTerm}/page/{pageNumber}")
	public ResponseEntity<List<InteractiveBook>> getByTitleOrCategory(@PathVariable String searchTerm, @PathVariable int pageNumber) throws Exception {
		
		try {
			
			pageNumber = pageNumber -1;
			Pageable pageable = PageRequest.of(pageNumber, 8);
			Page<InteractiveBook> page = interactiveBookRepository
					.findByTitleIgnoreCaseContainingOrBookCategories_CategoryNameIgnoreCaseContainingOrderByPublishDateDesc
					(searchTerm, searchTerm, pageable);
			List<InteractiveBook> books = page.getContent();
			
			return page.isFirst() && page.isEmpty() ? 
					ResponseEntity.notFound().build() : ResponseEntity.ok().body(books);
			
		}catch (Exception exception) {
			throw exception;
		}
	}

	@PostMapping
	public ResponseEntity<InteractiveBook> create(@RequestBody InteractiveBook interactiveBookBody) throws Exception {
		try {
			InteractiveBook newInteractiveBook = interactiveBookRepository.save(interactiveBookBody);
			return ResponseEntity.ok().body(newInteractiveBook);
		} catch (Exception exception) {
			throw exception;
		}
	}
	
	@PutMapping("{id}/upload_image")
	public void uploadImage(@RequestParam("imageFile") MultipartFile file, @PathVariable Integer id) throws Exception {
		InteractiveBook updatedInteractiveBook = interactiveBookRepository.findById(id)
				 .orElseThrow(() -> new NotFoundException ("not found" + id));
		
		updatedInteractiveBook.setBookCover(file.getBytes());
		interactiveBookRepository.save(updatedInteractiveBook);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<InteractiveBook> update(@RequestBody InteractiveBook interactiveBookBody, 
			@PathVariable Integer id) throws Exception {
		
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		
		interactiveBook.setBookAuthors(interactiveBookBody.getBookAuthors());
		interactiveBook.setBookCategories(interactiveBookBody.getBookCategories());
		interactiveBook.setBookCover(interactiveBookBody.getBookCover());
		interactiveBook.setChapters(interactiveBookBody.getChapters());
		interactiveBook.setCharacters(interactiveBookBody.getCharacters());
		interactiveBook.setPrice(interactiveBookBody.getPrice());
		interactiveBook.setPublishDate(interactiveBookBody.getPublishDate());
		interactiveBook.setQuests(interactiveBookBody.getQuests());
		interactiveBook.setSinopsys(interactiveBookBody.getSinopsys());
		interactiveBook.setTitle(interactiveBookBody.getTitle());
		
		System.out.println(interactiveBookBody);
		
		InteractiveBook updatedInteractiveBook = interactiveBookRepository.save(interactiveBook);
	    return ResponseEntity.ok().body(updatedInteractiveBook);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Integer id) throws Exception {
		
		InteractiveBook interactiveBook = interactiveBookRepository.findById(id)
				.orElseThrow(() -> new NotFoundException("not found"));
		interactiveBookRepository.delete(interactiveBook);
	}
	
}

package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{

}

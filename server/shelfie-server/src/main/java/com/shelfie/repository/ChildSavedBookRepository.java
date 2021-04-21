package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.ChildSavedBook;

public interface ChildSavedBookRepository extends JpaRepository<ChildSavedBook, Integer>{

}
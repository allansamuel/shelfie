package com.shelfie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shelfie.model.Character;

public interface CharacterRepository extends JpaRepository<Character, Integer>{

}

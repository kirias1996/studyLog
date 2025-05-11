package com.example.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.study.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer> {

	Tag findByTagName(String lowerCase);
}

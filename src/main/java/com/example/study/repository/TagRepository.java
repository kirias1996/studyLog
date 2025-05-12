package com.example.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {

	Tag findByTagName(String lowerCase);

	List<Tag> findTop10ByTagNameStartingWithIgnoreCaseOrderByTagName(String tagName);
}

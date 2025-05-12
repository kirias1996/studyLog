package com.example.study.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.study.dto.TagDto;
import com.example.study.repository.TagRepository;

@Service
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;
	
	public TagServiceImpl(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Override
	public List<TagDto> searchTags(String tagName) {
		return tagRepository
				.findTop10ByTagNameStartingWithIgnoreCaseOrderByTagName(tagName)
				.stream()
				.map(tag -> new TagDto(tag.getTagId(),tag.getTagName()))
				.toList();
	}

}

package com.example.study.service;

import java.util.List;

import com.example.study.dto.TagDto;

public interface TagService {
	List<TagDto> searchTags(String keyword);
}

package com.example.study.controller.tag;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.study.dto.TagDto;
import com.example.study.service.TagService;

@Controller
public class TagController {
	
	private final TagService tagService;
	
	public TagController(TagService tagService) {
		this.tagService = tagService;
	}
	
	@GetMapping("/tags/search")
	public ResponseEntity<List<TagDto>> searchTags(@RequestParam String keyword){
		//ブラウザ上で「/tags/search?keyword=」のようにアクセスされたときの回避ロジック
		if(keyword == null || keyword.trim().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(tagService.searchTags(keyword));
	}
}

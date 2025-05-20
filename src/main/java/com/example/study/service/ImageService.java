package com.example.study.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

	Map<String, Object> upload(MultipartFile file, Map<String, Object> param) throws IOException;

	void deleteFile(String publicId);

}

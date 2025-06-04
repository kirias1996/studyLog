package com.example.study.util;

import java.util.Map;

import com.cloudinary.utils.ObjectUtils;

public class CloudinaryParamsHelper {
	
	private CloudinaryParamsHelper() {
		
	}

	public static Map<String, Object> defaultParams(String publicId,String uploadFolder) {
		return ObjectUtils.asMap(
				"public_id", publicId,
				"overwrite", true,
				"folder",uploadFolder
				);
	}

}

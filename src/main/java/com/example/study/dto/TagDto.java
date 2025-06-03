package com.example.study.dto;

public class TagDto {
	/*このクラスは画面表示用にしか利用していないためバリデーションチェックは未実装*/
	private int tagId;
	private String tagName;

	public TagDto(int tagId, String tagName) {
		this.tagId = tagId;
		this.tagName = tagName;
	}

	public int getTagId() {
		return tagId;
	}

	public void setTagId(int tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}

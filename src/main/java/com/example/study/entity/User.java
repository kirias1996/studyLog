package com.example.study.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import org.springframework.util.StringUtils;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name = "auth_id",nullable = false)
	private Auth auth;

	@Size(max = 50)
	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "profile_text")
	private String profileText;

	@Size(max = 255)
	@Column(name = "icon_url")
	private String iconUrl;
	
	@Size(max = 255)
	@Column(name = "icon_public_id")
	private String iconPublicId;

	@Column(name = "created_at", insertable = true, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		if (Objects.isNull(this.createdAt)) {
			this.createdAt = LocalDateTime.now();
		}

		if (Objects.isNull(this.updatedAt)) {
			this.updatedAt = LocalDateTime.now();
		}
		// nullと空文字チェックを同時に判定する
		if (!StringUtils.hasText(this.userName)) {
			this.userName = "未設定ユーザー";
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfileText() {
		return profileText;
	}

	public void setProfileText(String profileText) {
		this.profileText = profileText;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public String getIconPublicId() {
		return iconPublicId;
	}

	public void setIconPublicId(String iconPublicId) {
		this.iconPublicId = iconPublicId;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

}

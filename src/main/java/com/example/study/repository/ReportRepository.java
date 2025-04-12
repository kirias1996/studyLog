package com.example.study.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study.dto.ReportRequestDto;
import com.example.study.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
	
	// ログインユーザの投稿を取得	
	List<Report> findByUserId(int userId);
	
	// 投稿済みの日報(1件)を取得
	Optional<Report> findById(int id);
	
	void save(ReportRequestDto dto);
}

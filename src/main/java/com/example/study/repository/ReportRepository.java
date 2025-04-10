package com.example.study.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.study.entity.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {
	
	// ログインユーザの投稿を取得	
	List<Report> findByUserId(int userId);
}

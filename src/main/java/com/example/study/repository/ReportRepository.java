package com.example.study.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
	
	void deleteById(int id);
	
	boolean existsById(int id);
	
	//累計学習時間の取得
	@Query("SELECT SUM(r.learningHours) FROM Report r WHERE r.userId = :userId")
	Double findTotalLearningTimes(int userId);
	
	//日報投稿日数の取得
	@Query("SELECT COUNT(DISTINCT r.learningDate) FROM Report r WHERE r.userId = :userId")
	Integer findTotalLearningDays(int userId);
	
	//連続日報投稿数の取得
	@Query("SELECT DISTINCT r.learningDate FROM Report r WHERE r.userId = :userId ORDER BY r.learningDate DESC")
	List<LocalDate> findLearningDays(int userId);
}

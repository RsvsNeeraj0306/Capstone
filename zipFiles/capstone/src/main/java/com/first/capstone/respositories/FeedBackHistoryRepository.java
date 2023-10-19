package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.FeedbackHistory;

public interface FeedBackHistoryRepository extends JpaRepository<FeedbackHistory, Long> {


    
}

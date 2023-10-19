package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.SoftwarePastHistory;

public interface SoftwarePastHistoryRepository extends JpaRepository<SoftwarePastHistory, Long>{
    
}

package com.first.capstone.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.entity.SoftwareLicenseHistory;



public interface SoftwareLicenseHistoryRepository extends JpaRepository<SoftwareLicenseHistory, Long> {
    // You can define custom query methods if needed.
    @Query("select s  from SoftwareLicenseHistory s where s.action='Renew'")
    List<SoftwareLicenseHistory> findAllByAction(String action);
}

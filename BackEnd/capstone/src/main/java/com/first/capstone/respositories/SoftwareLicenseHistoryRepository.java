package com.first.capstone.respositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.entity.SoftwareLicenseHistory;



public interface SoftwareLicenseHistoryRepository extends JpaRepository<SoftwareLicenseHistory, Long> {
    // You can define custom query methods if needed.
    @Query("select s  from SoftwareLicenseHistory s where s.action='Renew'")
    List<SoftwareLicenseHistory> findAllByAction(String action);

    @Query("SELECT s.action AS actionType, COUNT(s) AS actionCount FROM SoftwareLicenseHistory s GROUP BY s.action")
    List<ActionCount> countActions();

    interface ActionCount {
        String getActionType();
        Long getActionCount();
    }
}

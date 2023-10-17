package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.SoftwareLicenseHistory;

public interface SoftwareLicenseHistoryRepository extends JpaRepository<SoftwareLicenseHistory, Long> {
    // You can define custom query methods if needed.
}

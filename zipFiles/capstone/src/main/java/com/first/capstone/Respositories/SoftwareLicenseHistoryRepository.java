package com.first.capstone.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.Entity.SoftwareLicenseHistory;

public interface SoftwareLicenseHistoryRepository extends JpaRepository<SoftwareLicenseHistory, Long> {
    // You can define custom query methods if needed.
}

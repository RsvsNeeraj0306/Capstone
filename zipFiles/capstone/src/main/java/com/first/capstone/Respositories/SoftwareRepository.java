package com.first.capstone.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.Entity.Software;

public interface SoftwareRepository extends JpaRepository<Software, Long> {
    // You can define custom query methods if needed.
}

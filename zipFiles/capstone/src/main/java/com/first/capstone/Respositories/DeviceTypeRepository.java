package com.first.capstone.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.Entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    // You can define custom query methods if needed.
}

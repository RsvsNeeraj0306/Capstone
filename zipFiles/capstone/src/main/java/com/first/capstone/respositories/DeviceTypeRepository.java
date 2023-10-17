package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.DeviceType;

public interface DeviceTypeRepository extends JpaRepository<DeviceType, Long> {
    // You can define custom query methods if needed.
}

package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.entity.NetworkDeviceAttribute;

public interface NetworkDeviceAttributeRepository extends JpaRepository<NetworkDeviceAttribute, Long> {
    
}

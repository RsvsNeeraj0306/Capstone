package com.first.capstone.Respositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.first.capstone.Entity.NetworkDevice;

public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, Long> {
    // You can define custom query methods if needed.
}

package com.first.capstone.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.first.capstone.entity.NetworkDeviceRMA;

public interface NetworkDeviceRMARepository extends JpaRepository<NetworkDeviceRMA, Long> {
    
}

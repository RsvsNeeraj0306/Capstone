package com.first.capstone.respositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.first.capstone.entity.NetworkDevice;


public interface NetworkDeviceRepository extends JpaRepository<NetworkDevice, Long> {
    // You can define custom query methods if needed.

     @Query(value = "SELECT n FROM NetworkDevice n  WHERE n.manufacturer.fieldOfWork='Hardware'")
    List<NetworkDevice> findAllNetworkDevicesByManufacturer();

    Optional<NetworkDevice> findByIdAndHardwareName(Long id, String hardwareName);
}

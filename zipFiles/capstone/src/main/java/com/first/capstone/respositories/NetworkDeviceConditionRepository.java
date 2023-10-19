package com.first.capstone.respositories;

import org.springframework.data.repository.CrudRepository;

import com.first.capstone.entity.NetworkDeviceCondition;

public interface NetworkDeviceConditionRepository extends CrudRepository<NetworkDeviceCondition, Long> {
    
}

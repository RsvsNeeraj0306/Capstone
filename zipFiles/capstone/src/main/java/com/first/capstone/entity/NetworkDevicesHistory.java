package com.first.capstone.entity;



import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
 @Getter
 @Setter
 @RequiredArgsConstructor

public class NetworkDevicesHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deviceNameAndId;

    private String serialNumber;
    private BigDecimal cost;
    private Integer quantity;
    private Date purchaseDate;
    private Date warrantyEndDate;
    private String action;
}

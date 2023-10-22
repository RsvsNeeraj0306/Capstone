package com.first.capstone.entity;



import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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


    @ManyToOne
    private NetworkDevice networkDevice;
    private String licenseKey;
    private Date purchaseDate;
    private Date warrantyEndDate;
}

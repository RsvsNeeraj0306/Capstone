package com.first.capstone.entity;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.CascadeType;
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

public class NetworkDeviceRMA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date dateOfAction;
    private String reason;
    private String actionType;
    private BigDecimal amount; 

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private NetworkDevice networkDevice;


}

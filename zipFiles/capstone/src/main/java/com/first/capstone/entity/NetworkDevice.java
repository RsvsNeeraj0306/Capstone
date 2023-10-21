package com.first.capstone.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.sql.Date;


@Entity
 @Getter
 @Setter
 @RequiredArgsConstructor
public class NetworkDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Manufacturer manufacturer;

    private String hardwareName;
    private Date purchaseDate;
    private Date warrantyEndDate;
    private String location;
    private String serialNumber;
    private Integer quantity;
    private BigDecimal cost;
}







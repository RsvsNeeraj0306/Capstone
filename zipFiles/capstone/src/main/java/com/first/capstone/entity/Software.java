package com.first.capstone.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
 @Getter
 @Setter
 @RequiredArgsConstructor
public class Software {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Manufacturer manufacturer;

    private String softwareName;
    private Date purchaseDate;
    private Date expiryDate;
    private String typeOfPlan;
    private Integer usersCanUse;
    private BigDecimal priceOfSoftware;

}

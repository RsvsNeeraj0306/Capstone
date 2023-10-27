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

import jakarta.persistence.Table;


@Entity
 @Getter
 @Setter
 @RequiredArgsConstructor
 @Table(name = "SOFTWARE_LICENSE_HISTORY")
public class SoftwareLicenseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String softwareName;
    private String licenseKey;
    private Date purchaseDate;
    private Date expiryDate;
    private String typeOfPlan;
    private Integer quantity;
    private BigDecimal priceOfSoftware;
    private String version;
    private String action;
    private Date date;

}

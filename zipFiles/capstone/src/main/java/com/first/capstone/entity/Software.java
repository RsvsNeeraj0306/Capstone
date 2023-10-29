package com.first.capstone.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.CascadeType;
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


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE})
    private Manufacturer manufacturer;

    private String softwareName;
    private Date purchaseDate;
    private Date expiryDate;
    private String typeOfPlan;
    private String licenseKey;
    private Integer quantity;
    private String version;
    private BigDecimal priceOfSoftware;

    @Override
    public String toString() {
        return "Software{" +
                "id=" + id;
    }

}

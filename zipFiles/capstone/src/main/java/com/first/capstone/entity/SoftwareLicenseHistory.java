package com.first.capstone.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private Software software;

    private String licenseKey;

    private Date purchaseDate;
    private Date expiryDate;

}

package com.first.capstone.Entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Data
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

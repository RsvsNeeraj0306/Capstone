package com.first.capstone.Entity;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.util.Date;

@Entity
@Data
public class NetworkDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    private Manufacturer manufacturer;

    private String hardwareName;
    private Date purchaseDate;
    private Date warrantyEndDate;

    @ManyToOne(cascade = CascadeType.ALL)
    private DeviceType deviceType;

    @ManyToOne(cascade = CascadeType.ALL)
    private Location location;

    private String serialNumber;
}







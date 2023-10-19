package com.first.capstone.entity;

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
public class SoftwarePastHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date pastupdatedate;
    private Date pastRefunDate;
    private Integer pastRefundAmount; 

    @ManyToOne(cascade = CascadeType.ALL)
    private Software software;

    
}

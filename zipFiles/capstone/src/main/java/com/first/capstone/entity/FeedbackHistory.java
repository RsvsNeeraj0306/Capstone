package com.first.capstone.entity;



import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter
@Table(name = "feedback_history")
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private Integer feedbackRating;

        private String feedbackComment;
        private String feedbackDate;

        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "network_device_feedback_history", joinColumns = @JoinColumn(name = "feedback_history_id"), inverseJoinColumns = @JoinColumn(name = "network_device_id"))
        private List<NetworkDevice> networkDevice;


        @ManyToMany(cascade = CascadeType.ALL)
        @JoinTable(name = "Software_license_feedback_history", joinColumns = @JoinColumn(name = "feedback_history_id"), inverseJoinColumns = @JoinColumn(name = "software_id"))
        private List<Software> software;
}
